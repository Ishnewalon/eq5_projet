package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
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

    public OfferApplicationController(OfferApplicationService offerApplicationService) {
        this.offerApplicationService = offerApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> studentApplyToOffer(@RequestBody OfferAppDTO offerAppDTO) {//TODO : Check if student has valid curriculum
        try {
            offerApplicationService.create(offerAppDTO.getIdOffer(), offerAppDTO.getIdStudent());//FIXME: Change to offerAppDTO
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(CREATED)
                .body(new ResponseMessage("Candidature envoyé!"));
    }

    @GetMapping("/applicants/{email}")
    public ResponseEntity<?> viewApplicantList(@PathVariable String email) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getAllByOfferCreatorEmail(email);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(offerApplicationList);
    }


    @GetMapping("/applicants/manager/{id}")
    public ResponseEntity<?> getOffersApplicationsStageTrouver(@PathVariable Long id) {
        List<OfferApplication> offerApplicationList;
        try {
            offerApplicationList = offerApplicationService.getOffersApplicationsStageTrouverManagerId(id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(offerApplicationList);
    }

    @PostMapping("/setdate/{offerAppID}")
    public ResponseEntity<?> setInterviewDate(@PathVariable Long offerAppID, @RequestBody LocalDateTime date) {
        try {
            OfferApplication offerApplication = offerApplicationService.setInterviewDate(offerAppID, date);
            return ResponseEntity.ok(offerApplication);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/applicants/cv_sent/{id}")
    public ResponseEntity<?> getAllByOfferStatusAndStudentID(@PathVariable Long id) {
        try {
            List<OfferApplication> offerApplicationList = offerApplicationService.getAllByOfferStatusAndStudentID(Status.CV_ENVOYE, id);
            return ResponseEntity.ok(offerApplicationList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/applicants/supervisor")
    public ResponseEntity<?> getOffersApplicationsStageTrouver() {
        List<Student> studentList = offerApplicationService.getOffersApplicationsStageTrouver();

        return ResponseEntity.ok(studentList);
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
