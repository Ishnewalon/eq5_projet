package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.CurriculumDTO;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.exception.StudentHasNoCurriculumException;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.service.CurriculumService;
import com.gestionnaire_de_stage.service.OfferApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
            offerApplicationService.create(offerAppDTO.getIdOffer(), offerAppDTO.getIdStudent());
        } catch (StudentAlreadyAppliedToOfferException err) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Candidature déjà envoyé!"));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre ou étudiant non existant!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (StudentHasNoCurriculumException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Vous devez d'abord ajouter un curriculum!"));
        }
        return ResponseEntity
                .status(CREATED)
                .body(new ResponseMessage("Candidature envoyé!"));
    }

    @GetMapping("/applicants/{email}")
    public ResponseEntity<?> viewApplicantList(@PathVariable String email) {
        List<OfferApplication> offerApplicationList;
        List<CurriculumDTO> curriculumDTOList;
        try {
            offerApplicationList = offerApplicationService.getAllByOfferCreatorEmail(email);
            curriculumDTOList = curriculumService.mapToCurriculumDTOList(offerApplicationList);
        } catch (EmailDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le courriel n'existe pas"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(curriculumDTOList);
    }

    @PostMapping("/setdate/{offerAppID}")
    public ResponseEntity<?> setInterviewDate(@PathVariable Long offerAppID, @RequestBody LocalDateTime date) {
        try{
            OfferApplication offerApplication = offerApplicationService.setInterviewDate(offerAppID, date);
            return ResponseEntity.ok(offerApplication);
        } catch (DateNotValidException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("La date entrée est invalide!"));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Impossible de trouver l'offre avec cette ID!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/all_applied_on/{studentID}")
    public ResponseEntity<?> getAllByOfferStatusAndStudentID(@PathVariable Long studentID) {
        try {
            List<OfferApplication> offerApplicationList = offerApplicationService.getAllByOfferStatusAndStudentID(Status.CV_ENVOYE, studentID);
            return ResponseEntity.ok(offerApplicationList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/applicants/student/{id}")
    public ResponseEntity<?> getAllOffersApplied(@PathVariable Long id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getAllOffersStudentApplied(id);
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("L'étudiant n'existe pas"));
        } catch (IllegalArgumentException e){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(offerApplicationList);
    }

    @PostMapping("/student/update_status")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        boolean isAccepted;
        try {
            isAccepted = offerApplicationService.updateStatus(updateStatusDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre non existante!"));
        }
        String message = isAccepted ? "Status changé, attendez la signature du contrat" : "Status changé, stage refusé";
        return ResponseEntity.ok(new ResponseMessage(message));
    }
}
