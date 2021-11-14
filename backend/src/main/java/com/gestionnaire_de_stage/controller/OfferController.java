package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.exception.OfferAlreadyTreatedException;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
@CrossOrigin
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addOffer(@RequestBody OfferDTO dto) {
        Offer offer;
        try {
            offer = offerService.create(dto);
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(ie.getMessage()));
        } catch (OfferAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre existe déjà"));
        } catch (EmailDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le courriel n'existe pas"));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offer);
    }

    @GetMapping({"/", "/{department}"}) //FIXME Handle exception
    public ResponseEntity<?> getOffersByDepartment(@PathVariable(required = false) String department) {//TODO get the student ID instead of the department
        List<Offer> offers;
        try {
            offers = offerService.getOffersByDepartment(department);//TODO: get only offers non applied
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }

        return ResponseEntity.ok(offers);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateOffer(@RequestBody ValidationOffer validationOffer) {
        try {
            Offer offer = offerService.validation(validationOffer);
            return ResponseEntity.ok(offer);
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre non existante!"));
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .badRequest()
                    .body(ie.getMessage());
        } catch (OfferAlreadyTreatedException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre déjà traité!"));
        }
    }

    @GetMapping("/valid")
    public ResponseEntity<?> getValidOffers() {
        List<Offer> offers = offerService.getValidOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/not_validated")
    public ResponseEntity<?> getNotValidatedOffers() {
        List<Offer> offers = offerService.getNotValidatedOffers();
        return ResponseEntity.ok(offers);
    }
}
