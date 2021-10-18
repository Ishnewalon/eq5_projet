package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorService(MonitorRepository monitorRepository){
        this.monitorRepository = monitorRepository;
    }

    public Monitor create(Monitor monitor) throws MonitorAlreadyExistsException, IllegalArgumentException {
        Assert.isTrue(monitor != null, "Monitor est null");
        if (emailAlreadyInUse(monitor)) {
            throw new MonitorAlreadyExistsException();
        }
        return monitorRepository.save(monitor);
    }

    public Monitor getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!monitorRepository.existsById(aLong)) {
            throw new IdDoesNotExistException();
        }
        return monitorRepository.getById(aLong);
    }

    public List<Monitor> getAll() {
        return monitorRepository.findAll();
    }

    public Monitor update(Monitor monitor, Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        Assert.isTrue(monitor != null, "Monitor est null");
        if (!monitorRepository.existsById(aLong)) {
            throw new IdDoesNotExistException();
        }
        monitor.setId(aLong);
        return monitorRepository.save(monitor);
    }

    public Monitor getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(email != null, "Le courriel est null");
        Assert.isTrue(password != null, "Le mot de passe est null");
        if (!monitorRepository.existsByEmailAndPassword(email, password)) {
            throw new EmailAndPasswordDoesNotExistException();
        }
        return monitorRepository.findMonitorByEmailAndPassword(email, password);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!monitorRepository.existsById(aLong)) {
            throw new IdDoesNotExistException();
        }
        monitorRepository.deleteById(aLong);
    }

    private boolean emailAlreadyInUse(Monitor monitor) {
        return monitor.getEmail() != null && monitorRepository.existsByEmail(monitor.getEmail());
    }
}
