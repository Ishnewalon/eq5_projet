package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import com.gestionnaire_de_stage.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    private final MonitorRepository monitorRepository;

    public MonitorController(MonitorService monitorService, MonitorRepository monitorRepository) {
        this.monitorService = monitorService;
        this.monitorRepository = monitorRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Monitor monitor) {
        Monitor createdMonitor;
        try{
            createdMonitor = monitorService.create(monitor);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdMonitor);
        } catch (MonitorAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe deja!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Le courriel ne peut pas etre null"));
        }
    }


    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email,@PathVariable String password) {
        Monitor monitor;
        try{
            monitor = monitorService.getOneByEmailAndPassword(email,password);
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(monitor);
        } catch (EmailAndPasswordDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalide"));
        } catch (IllegalArgumentException e){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Le courriel et le mot de passe ne peuvent pas etre null"));
        }
    }
}
