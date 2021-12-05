package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            Manager manager = managerService.getOneByEmailAndPassword(email, password);
            return ResponseEntity.ok(manager);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkValidEmail(@PathVariable String email) {
        return ResponseEntity.ok(managerService.isEmailInvalid(email));
    }
}
