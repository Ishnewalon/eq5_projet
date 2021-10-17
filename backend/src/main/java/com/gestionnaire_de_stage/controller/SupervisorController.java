package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import com.gestionnaire_de_stage.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/supervisor")
public class SupervisorController {

    private final SupervisorService supervisorService;

    private final SupervisorRepository supervisorRepository;

    public SupervisorController(SupervisorService supervisorService, SupervisorRepository supervisorRepository) {
        this.supervisorService = supervisorService;
        this.supervisorRepository = supervisorRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Supervisor supervisor) {
        Supervisor createdSupervisor;
        try {
            createdSupervisor = supervisorService.create(supervisor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Le courriel ne peut pas être null"));
        } catch (SupervisorAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe deja!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupervisor);
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Supervisor supervisor = null;
        try {
            supervisor = supervisorService.getOneByEmailAndPassword(email, password);
        } catch (EmailAndPasswordDoesNotExistException e) {
            e.printStackTrace();
        }
        if (supervisor != null) {
            return ResponseEntity.ok(supervisor);
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalide"));
    }
}
