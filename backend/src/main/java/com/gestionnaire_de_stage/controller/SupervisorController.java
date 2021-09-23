package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import com.gestionnaire_de_stage.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    SupervisorService supervisorService;

    @Autowired
    SupervisorRepository supervisorRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Supervisor supervisor) {
        if (supervisor.getEmail() != null && supervisorRepository.existsByEmail(supervisor.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe deja!"));
        }
        Optional<Supervisor> opt = supervisorService.create(supervisor);
        Supervisor supervisor1 = opt.get();

        return new ResponseEntity<>(supervisor1, HttpStatus.CREATED);
    }

}
