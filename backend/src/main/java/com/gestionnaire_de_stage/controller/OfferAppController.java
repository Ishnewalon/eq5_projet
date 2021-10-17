package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.service.OfferAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class OfferAppController {
    private final OfferAppService offerAppService;

    public OfferAppController(OfferAppService offerAppService) {
        this.offerAppService = offerAppService;
    }


    @PostMapping("/apply")
    public ResponseEntity<?> studentApplyToOffer(@RequestBody OfferAppDTO offerAppDTO) {
        try {
            offerAppService.create(offerAppDTO.getIdOffer(), offerAppDTO.getIdCurriculum());
        } catch (StudentAlreadyAppliedToOfferException err) {
            return new ResponseEntity<>(new ResponseMessage("Erreur: candidature deja envoye!"), HttpStatus.ALREADY_REPORTED);
        } catch (IdDoesNotExistException e) {
            return new ResponseEntity<>(new ResponseMessage("Erreur: Offre ou Curriculum non existant!"), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Succes: candidature envoyer!"), HttpStatus.CREATED);
    }
}
