package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.MonitorService;
import com.gestionnaire_de_stage.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
@CrossOrigin
public class OfferController {

    private final MonitorService monitorService;

    private final OfferService offerService;

    public OfferController(MonitorService monitorService, OfferService offerService) {
        this.offerService = offerService;
        this.monitorService = monitorService;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAll();
    }


    @PostMapping("/add")
    public ResponseEntity<?> addOfferMonitor(@RequestBody OfferDTO dto) {
        Offer offer = offerService.mapToOffer(dto);
        System.out.println(dto.getCreator_email());
        Monitor monitor = monitorService.getOneByEmail(dto.getCreator_email());
        offer.setCreator(monitor);
        try {

            offer = offerService.create(offer);
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(ie.getMessage()));
        } catch (OfferAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre existe déjà"));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offer);
    }

    @GetMapping({"/", "/{department}"}) //TODO Handle exception
    public ResponseEntity<?> getOffersByDepartment(@PathVariable(required = false) String department) {
        if (department == null || department.isEmpty() || department.isBlank())
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le département n'est pas précisé"));

        List<OfferDTO> offerDTOS = offerService.getOffersByDepartment(department);

        return ResponseEntity.ok(offerDTOS);
    }

    @PutMapping("/validate")
    public ResponseEntity<?> validateOffer(@RequestBody Offer o) {
        try {
            Offer offer = offerService.update(o);
            return ResponseEntity
                    .ok(offer);
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre non existante!"));
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .badRequest()
                    .body(ie.getMessage());
        }
    }

}
