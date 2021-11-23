package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.CurriculumDTO;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.DateNotValidException;
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
    public ResponseEntity<?> studentApplyToOffer(@RequestBody OfferAppDTO offerAppDTO) {//TODO : Check if student has valid curriculum
        try {
            offerApplicationService.create(offerAppDTO.getIdOffer(), offerAppDTO.getIdStudent());//FIXME: Change to offerAppDTO
        } catch (StudentAlreadyAppliedToOfferException err) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Candidature déjà envoyé!"));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
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
            offerApplicationList = offerApplicationService.getAllByOfferCreatorEmail(email);//SESSION : get only offer of current session or future
            curriculumDTOList = curriculumService.mapToCurriculumDTOList(offerApplicationList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(curriculumDTOList);//TODO: Send list offer with list of cv_applicants
    }

    @PostMapping("/setdate/{offerAppID}")
    public ResponseEntity<?> setInterviewDate(@PathVariable Long offerAppID, @RequestBody LocalDateTime date) {
        try {
            OfferApplication offerApplication = offerApplicationService.setInterviewDate(offerAppID, date);//SESSION : check if offer is not outdated
            return ResponseEntity.ok(offerApplication);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/all_applied_on/{studentID}")//SESSION : get offers of current session and future
    public ResponseEntity<?> getAllByOfferStatusAndStudentID(@PathVariable Long studentID) {
        try {
            List<OfferApplication> offerApplicationList = offerApplicationService.getAllByOfferStatusAndStudentID(Status.CV_ENVOYE, studentID);
            return ResponseEntity.ok(offerApplicationList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/applicants/manager/{id}")
    public ResponseEntity<?> getOffersApplicationsStageTrouver(@PathVariable Long id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getOffersApplicationsStageTrouver(id);//SESSION : get application of current session and future
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(offerApplicationList);
    }

    @GetMapping("/applicants/student/{id}")
    public ResponseEntity<?> getAllOffersAppliedByStatus(@PathVariable Long id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getAllOffersStudentAppliedAndStatusWaiting(id);//SESSION : get offers of current session and future
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(offerApplicationList);
    }

    @PostMapping("/student/update_status")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        String message;
        try {
            message = offerApplicationService.updateStatus(updateStatusDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage(message));
    }

    @GetMapping("/applicants/offerApp/student/{id}")
    public ResponseEntity<?> getAllOffersApplied(@PathVariable Long id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getAllOffersStudentApplied(id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(offerApplicationList);
    }

    @GetMapping
    public ResponseEntity<?> getAllOffersApplication() {
        return ResponseEntity.ok(offerApplicationService.getAllOffersApplication());
    }
}
