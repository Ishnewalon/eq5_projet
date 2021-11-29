package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Monitor monitor) {

        try {
            Monitor createdMonitor = monitorService.create(monitor);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdMonitor);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkValidEmail(@PathVariable String email) {
        return ResponseEntity.ok(monitorService.isEmailInvalid(email));
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {

        try {
            Monitor monitor = monitorService.getOneByEmailAndPassword(email, password);
            return ResponseEntity.ok(monitor);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<?> UpdatePassword(@PathVariable Long id, @RequestBody String password) {
        monitorService.changePassword(id, password);
        return ResponseEntity.ok(new ResponseMessage("Mot de passe changé avec succès"));
    }
}
