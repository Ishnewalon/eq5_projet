package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorService(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    public Monitor create(Monitor monitor) throws MonitorAlreadyExistsException, IllegalArgumentException {
        Assert.isTrue(monitor != null, "Le moniteur ne peut pas être vide");
        if (emailAlreadyInUse(monitor))
            throw new MonitorAlreadyExistsException("Un compte existe déjà pour ce moniteur");
        return monitorRepository.save(monitor);
    }

    public Monitor getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!monitorRepository.existsById(aLong))
            throw new IdDoesNotExistException("Il n'y a pas de moniteur associé à cet identifiant");
        return monitorRepository.getById(aLong);
    }

    public Monitor update(Monitor monitor) throws IdDoesNotExistException {
        Assert.isTrue(monitor != null, "Monitor est null");
        if (!monitorRepository.existsById(monitor.getId()))
            throw new IdDoesNotExistException("Il n'y a pas de moniteur associé à cet identifiant");
        return monitorRepository.save(monitor);
    }

    public Monitor getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être vide");
        Assert.isTrue(password != null, "Le mot de passe ne peut pas être vide");
        if (!monitorRepository.existsByEmailAndPassword(email, password))
            throw new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalide");
        return monitorRepository.findMonitorByEmailAndPassword(email, password);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!monitorRepository.existsById(aLong))
            throw new IdDoesNotExistException("Il n'y a pas de moniteur associé à cet identifiant");
        monitorRepository.deleteById(aLong);
    }

    public Monitor getOneByEmail(String email) throws DoesNotExistException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être vide");
        if (isEmailInvalid(email)) {
            throw new DoesNotExistException("Le courriel n'existe pas");
        }
        return monitorRepository.getMonitorByEmail(email);
    }

    private boolean emailAlreadyInUse(Monitor monitor) {
        return monitor.getEmail() != null && monitorRepository.existsByEmail(monitor.getEmail());
    }

    public boolean isEmailInvalid(String email) {
        return !monitorRepository.existsByEmail(email);
    }

    public boolean isIdInvalid(Long id) {
        return !monitorRepository.existsById(id);
    }

    public Monitor changePassword(Long id, String password) throws IdDoesNotExistException {
        Monitor monitor = getOneByID(id);
        monitor.setPassword(password);
        return monitorRepository.save(monitor);
    }
}
