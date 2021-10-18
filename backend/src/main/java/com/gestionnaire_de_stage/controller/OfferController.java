package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.ManagerService;
import com.gestionnaire_de_stage.service.MonitorService;
import com.gestionnaire_de_stage.service.OfferService;
import com.sun.mail.iap.Response;
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
    public ResponseEntity<?> addOfferMonitor(@RequestBody OfferDTO offerDTO) {
        Offer offer = offerService.mapToOffer(offerDTO);

        try {
            Monitor monitor = monitorService.getOneByID(offerDTO.getCreator_id());
            offer.setCreator(monitor);

            offer = offerService.create(offer);
            return new ResponseEntity<>(offer, HttpStatus.CREATED);
        }catch (IllegalArgumentException ie){
                return ResponseEntity
                        .badRequest()
                        .body(new ResponseMessage(ie.getMessage()));
        }catch(IdDoesNotExistException ide){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le moniteur n'existe pas"));
        } catch (OfferAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre existe déjà"));
        }
    }

    @PostMapping("/manager/add")
    public ResponseEntity<?> addOfferManager(@RequestBody OfferDTO offerDTO) {
        Offer offer = offerService.mapToOffer(offerDTO);

        try {
            Manager manager = managerService.getOneByID(offerDTO.getCreator_id());
            offer.setCreator(manager);

            offer = offerService.create(offer);
            return new ResponseEntity<>(offer, HttpStatus.CREATED);
        }catch (IllegalArgumentException ie){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(ie.getMessage()));
        }catch(IdDoesNotExistException ide){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("L'offre existe déjà"));
        } catch (OfferAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Offre existe déjà"));
        }
    }

    @GetMapping({"/", "/{department}"}) //TODO Handle exception
    public ResponseEntity<?> getOffersByDepartment(@PathVariable(required = false) String department) {
        if (department == null || department.isEmpty() || department.isBlank())
            return ResponseEntity.badRequest().body(new ResponseMessage("Le departement n'est pas precise"));

        List<OfferDTO> offerDTOS = offerService.getOffersByDepartment(department);

        return new ResponseEntity<>(offerDTOS, HttpStatus.OK);
    }

    @PutMapping("/validate")
    public ResponseEntity<?> validateOffer(@RequestBody Offer o) {
        try{
            Offer offer = offerService.update(o);
            return ResponseEntity.accepted().body(offer);
        }catch (IdDoesNotExistException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Offre non existante!"));
        }catch (IllegalArgumentException ie){
            return ResponseEntity.badRequest().body(ie.getMessage());
        }
    }

}
