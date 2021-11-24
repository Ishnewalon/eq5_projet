package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.ManagerAlreadyExistsException;
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

    public Manager create(Manager manager) throws ManagerAlreadyExistsException { //FIXME Never Used other than tests
        Assert.isTrue(manager != null, "Le gestionnaire ne peut pas être vide");
        if (isNotValid(manager)) {
            throw new ManagerAlreadyExistsException("Un compte existe déjà pour ce gestionnaire");
        }
        return managerRepository.save(manager);
    }

    public Manager getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "L'identifiant du gestionnaire ne peut pas être vide");
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException("Il n'y a pas de gestionnaire associé à cet identifiant");
        }
        return managerRepository.getById(aLong);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }   //FIXME Never Used other than tests

    public Manager update(Manager manager, Long aLong) throws IdDoesNotExistException { //FIXME Never Used other than tests
        Assert.isTrue(aLong != null, "ID est null");
        Assert.isTrue(manager != null, "Le gestionnaire est null");
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException("Il n'y a pas de gestionnaire associé à cet identifiant");
        }
        manager.setId(aLong);
        return managerRepository.save(manager);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException { //FIXME Never Used other than tests
        Assert.isTrue(aLong != null, "ID est null");
        if (isIDNotValid(aLong))
            throw new IdDoesNotExistException("Il n'y a pas de gestionnaire associé à cet identifiant");
        managerRepository.deleteById(aLong);
    }

    public Manager getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être vide");
        Assert.isTrue(password != null, "Le mot de passe ne peut pas être vide");
        if (!isEmailAndPasswordValid(email, password))
            throw new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalid");
        return managerRepository.findManagerByEmailAndPassword(email, password);
    }

    private boolean isNotValid(Manager manager) {
        return manager.getEmail() != null && managerRepository.existsByEmail(manager.getEmail());
    }

    public boolean isIDNotValid(Long id) {
        return !managerRepository.existsById(id);
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return managerRepository.existsByEmailAndPassword(email, password);
    }


}