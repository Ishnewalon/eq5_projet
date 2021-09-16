package com.gestionnaire_de_stage.controllers;

import com.gestionnaire_de_stage.models.User;
import com.gestionnaire_de_stage.repository.UserRepository;
import com.gestionnaire_de_stage.web.security.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gestionnaires")
public class GestionnaireController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<User> getAll(){
        return userRepository.findAllByAccountType(AccountType.GESTIONNAIRE);
    }
}