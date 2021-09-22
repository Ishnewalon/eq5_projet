package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import com.gestionnaire_de_stage.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private MonitorRepository monitorRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Monitor monitor) {
        if (monitor.getEmail() != null && monitorRepository.existsByEmail(monitor.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: Ce courriel existe deja!"));
        }

        return ResponseEntity.ok(monitorService.create(monitor));
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

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Optional<Monitor> monitor = monitorService.getOneByEmailAndPassword(email, password);
        if (monitor.isPresent()) {
            return ResponseEntity.ok(monitor.get());
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Erreur: Courriel ou Mot de Passe Invalid"));
    }
}
