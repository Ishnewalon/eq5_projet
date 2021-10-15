package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import com.gestionnaire_de_stage.service.CurriculumService;
import com.gestionnaire_de_stage.service.OfferAppService;
import com.gestionnaire_de_stage.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class OfferAppController {
    private final OfferAppService offerAppService;
    private final OfferService offerService;
    private final CurriculumService curriculumService;

    public OfferAppController(OfferAppService offerAppService, OfferService offerService, CurriculumService curriculumService) {
        this.offerAppService = offerAppService;
        this.offerService = offerService;
        this.curriculumService = curriculumService;
    }


    @PostMapping("/apply")
    public ResponseEntity<?> studentApplyToOffer(@Valid @RequestBody OfferAppDTO offerAppDTO) {
        Optional<Offer> offer = offerService.findOfferById(offerAppDTO.getIdOffer());
        Curriculum curriculum = curriculumService.getCurriculum(offerAppDTO.getIdCurriculum());
        if (offer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: Offre non existant!");
        } else {
            Optional<OfferApp> offerApp = offerAppService.create(offer.get(), curriculum);
            return new ResponseEntity<>(offerApp.isPresent(), HttpStatus.CREATED);
        }
    }
}
