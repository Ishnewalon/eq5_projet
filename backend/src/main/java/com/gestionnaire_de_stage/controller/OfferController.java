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

    @PostMapping("/monitor/add")
    public ResponseEntity<?> addOfferMonitor(@RequestBody OfferDTO dto) {
        Offer offer = offerService.mapToOffer(dto);

        try {
            Monitor monitor = monitorService.getOneByID(dto.getCreator_id());
            offer.setCreator(monitor);

            offer = offerService.create(offer);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(offer);
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(ie.getMessage()));
        } catch (IdDoesNotExistException ide) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le moniteur n'existe pas"));
        } catch (OfferAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre existe déjà"));
        }
    }

    @PostMapping("/manager/add/{monitorEmail}")
    public ResponseEntity<?> addOfferManager(@RequestBody OfferDTO offerDTO, @PathVariable String monitorEmail) {
        Offer offer = offerService.mapToOffer(offerDTO);
        try {
            Monitor monitor = monitorService.getOneByEmail(monitorEmail);
            offer.setCreator(monitor);
            offer = offerService.create(offer);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(offer);
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(ie.getMessage()));
        } catch (OfferAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre existe déjà"));
        }
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
