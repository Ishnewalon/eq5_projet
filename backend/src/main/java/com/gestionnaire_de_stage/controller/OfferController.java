package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
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
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offer);
    }

    @GetMapping({"/{studentId}"})
    public ResponseEntity<?> getListOffersNotYetApplied(@PathVariable Long studentId) {
        List<Offer> offers;
        try {
            offers = offerService.getOffersNotYetApplied(studentId);
        } catch (IdDoesNotExistException e) {
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
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
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
