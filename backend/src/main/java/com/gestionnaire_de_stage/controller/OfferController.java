package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.ManagerService;
import com.gestionnaire_de_stage.service.MonitorService;
import com.gestionnaire_de_stage.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
@CrossOrigin
public class OfferController {

    private final MonitorService monitorService;

    private final ManagerService managerService;

    private final OfferService offerService;

    public OfferController(MonitorService monitorService, ManagerService managerService, OfferService offerService) {
        this.offerService = offerService;
        this.managerService = managerService;
        this.monitorService = monitorService;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidRequests(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessage> handleEmptyRequestBody(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ResponseMessage(ex.getMessage()));
    }

    @GetMapping
    public List<Offer> getAllOffers(){
        return offerService.getAll();
    }


    @PostMapping("/monitor/add")
    public ResponseEntity<?> addOfferMonitor(@Valid @RequestBody OfferDTO offerDTO) {
        Optional<Monitor> monitor = monitorService.getOneByID(offerDTO.getCreator_id());

        if (monitor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: moniteur non existant!");
        } else {
            Offer offer = offerService.mapToOffer(offerDTO);
            offer.setCreator(monitor.get());
            Optional<Offer> optionalOffer = offerService.create(offer);
            return new ResponseEntity<>(optionalOffer.isPresent(), HttpStatus.CREATED);
        }
    }

    @PostMapping("/manager/add")
    public ResponseEntity<?> addOfferManager(@Valid @RequestBody OfferDTO offerDTO) {
        Optional<Manager> manager = managerService.getOneByID(offerDTO.getCreator_id());

        if (manager.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: gestionnaire non existant!");
        } else {
            Offer offer = offerService.mapToOffer(offerDTO);
            offer.setCreator(manager.get());
            Optional<Offer> optionalOffer = offerService.create(offer);
            return new ResponseEntity<>(optionalOffer.isPresent(), HttpStatus.CREATED);
        }
    }

    @GetMapping({"/", "/{department}"}) //TODO Handle exception
    public ResponseEntity<?> getOffersByDepartment(@PathVariable(required = false) String department) {
        if (department == null || department.isEmpty() || department.isBlank())
            return ResponseEntity.badRequest().body(new ResponseMessage("Erreur: Le departement n'est pas precise"));

        List<OfferDTO> offerDTOS = offerService.getOffersByDepartment(department);

        return new ResponseEntity<>(offerDTOS, HttpStatus.OK);
    }

    @PutMapping("/validate")
    public ResponseEntity<?> validateOffer(@RequestBody Offer offer) {
        Optional<Offer> optionalOffer = offerService.update(offer);

        if(optionalOffer.isEmpty())
            return ResponseEntity.badRequest().body("Erreur : offre non existante!");
        return ResponseEntity.accepted().body(optionalOffer.get());
    }

}
