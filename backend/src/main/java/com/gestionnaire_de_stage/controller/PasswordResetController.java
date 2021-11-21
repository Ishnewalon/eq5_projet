package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/forgot_password")
public class PasswordResetController {
    private final String MESSAGE_SUCCESS = "Un email vous a été envoyé pour réinitialiser votre mot de passe";
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/monitor")
    public ResponseEntity<?> forgotPasswordMonitor(@RequestBody String email) {
        try {
            passwordResetService.forgotPasswordMonitor(email);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage(MESSAGE_SUCCESS));
    }

    @PostMapping("/supervisor")
    public ResponseEntity<?> forgotPasswordSupervisor(@RequestBody String email) {
        try {
            passwordResetService.forgotPasswordSupervisor(email);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage(MESSAGE_SUCCESS));
    }
}
