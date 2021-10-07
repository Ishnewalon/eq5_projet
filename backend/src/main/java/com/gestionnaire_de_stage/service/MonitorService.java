package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class MonitorService implements ICrudService<Monitor, Long> {

    @Autowired
    private MonitorRepository monitorRepository;
    //test

    @Override
    public Optional<Monitor> create(Monitor monitor) throws ValidationException {
        if (monitor != null) {
            return Optional.of(monitorRepository.save(monitor));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Monitor> getOneByID(Long aLong) {
        if (aLong != null && monitorRepository.existsById(aLong)) {
            return Optional.of(monitorRepository.getById(aLong));
        }
        return Optional.empty();
    }

    @Override
    public List<Monitor> getAll() {
        return monitorRepository.findAll();
    }

    @Override
    public Optional<Monitor> update(Monitor monitor, Long aLong) throws ValidationException {
        if (aLong != null && monitorRepository.existsById(aLong) && monitor != null) {
            monitor.setId(aLong);
            return Optional.of(monitorRepository.save(monitor));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Monitor> getOneByEmailAndPassword(String email, String password) {
        if (monitorRepository.existsByEmailAndPassword(email, password)) {
            return Optional.of(monitorRepository.findMonitorByEmailAndPassword(email, password));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteByID(Long aLong) {
        if (aLong != null && monitorRepository.existsById(aLong)) {
            monitorRepository.deleteById(aLong);
            return true;
        }
        return false;
    }
}
