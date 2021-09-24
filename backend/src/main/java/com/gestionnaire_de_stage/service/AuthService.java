package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ManagerService managerService;

    public Manager loginManager(String email, String password){
        return managerService.getOneByEmailAndPassword(email, password).orElse(null);
    }
}