package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.CurriculumDTO;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.service.CurriculumService;
import com.gestionnaire_de_stage.service.OfferApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class OfferApplicationController {
    private final OfferApplicationService offerApplicationService;
    private final CurriculumService curriculumService;

    public OfferApplicationController(OfferApplicationService offerApplicationService, CurriculumService curriculumService) {
        this.offerApplicationService = offerApplicationService;
        this.curriculumService = curriculumService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> studentApplyToOffer(@RequestBody OfferAppDTO offerAppDTO) {
        try {
            offerApplicationService.create(offerAppDTO.getIdOffer(), offerAppDTO.getIdCurriculum());
        } catch (StudentAlreadyAppliedToOfferException err) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: candidature deja envoye!"));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Offre ou Curriculum non existant!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(CREATED)
                .body(new ResponseMessage("Succes: candidature envoyer!"));
    }

    @GetMapping("/applicants/{email}")
    public ResponseEntity<?> viewApplicantList(@PathVariable String email) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getAllByOfferCreatorEmail(email);
        } catch (EmailDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        List<CurriculumDTO> curriculumDTOList = curriculumService.mapToCurriculumDTOList(offerApplicationList);

        return ResponseEntity
                .ok(curriculumDTOList);
    }
}
