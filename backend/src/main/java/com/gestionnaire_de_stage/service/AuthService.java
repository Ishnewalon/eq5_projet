package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ManagerService managerService;

    public AuthService(ManagerService managerService) {
        this.managerService = managerService;
    }

    public Manager loginManager(String email, String password){
        return managerService.getOneByEmailAndPassword(email, password).orElse(null);
    }
}