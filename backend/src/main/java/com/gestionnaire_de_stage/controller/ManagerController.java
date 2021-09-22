package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/managers")
@CrossOrigin
public class ManagerController {

    @Autowired
    private AuthService authService;

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email,@PathVariable String password){
        return ResponseEntity.of(Optional.of(authService.loginManager(email, password)));
    }
}
