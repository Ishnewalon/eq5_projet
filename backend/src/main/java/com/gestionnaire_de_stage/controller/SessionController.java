package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@CrossOrigin
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        try {
            sessionService.createSession(session);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .ok(new ResponseMessage("Session créée avec succès!"));
    }

    @GetMapping
    public ResponseEntity<?> getActualAndFutureSessions() {
        return ResponseEntity.ok(sessionService.getActualAndFutureSessions());
    }
}


