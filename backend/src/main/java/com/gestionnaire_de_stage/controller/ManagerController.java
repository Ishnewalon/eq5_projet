package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    @Autowired
    private AuthService authService;

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email,@PathVariable String password){
        HttpStatus status = HttpStatus.OK;
        Manager manager = null;
        try{
            manager = authService.loginManager(email, password);
        }catch (RuntimeException re){
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(manager);
    }


}
