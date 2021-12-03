package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.notNull(aLong, "L'identifiant du gestionnaire ne peut pas être vide");
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException("Il n'y a pas de gestionnaire associé à cet identifiant");
        }
        return managerRepository.getById(aLong);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Manager getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.notNull(email, "Le courriel ne peut pas être vide");
        Assert.notNull(password, "Le mot de passe ne peut pas être vide");
        if (!isEmailAndPasswordValid(email, password))
            throw new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalide");
        return managerRepository.findManagerByEmailAndPassword(email, password);
    }

    public boolean isEmailInvalid(String email) {
        return !managerRepository.existsByEmail(email);
    }

    public boolean isIDNotValid(Long id) {
        return !managerRepository.existsById(id);
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return managerRepository.existsByEmailAndPassword(email, password);
    }

}