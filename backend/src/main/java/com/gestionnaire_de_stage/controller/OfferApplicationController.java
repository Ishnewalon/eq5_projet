package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.CurriculumDTO;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.exception.StudentHasNoCurriculumException;
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
    public ResponseEntity<?> viewApplicantList(@PathVariable String email) {//TODO: Return a list of curriculum with a list of applicants inside
        List<OfferApplication> offerApplicationList;
        List<CurriculumDTO> curriculumDTOList;
        try {
            offerApplicationList = offerApplicationService.getAllByOfferCreatorEmail(email);
            curriculumDTOList = curriculumService.mapToCurriculumDTOList(offerApplicationList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(curriculumDTOList);
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
        try {
            offerApplicationService.updateStatus(updateStatusDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre non existante!"));
        }
        return ResponseEntity.ok(new ResponseMessage("Status changé, attendez la signature du contrat"));
    }
}
