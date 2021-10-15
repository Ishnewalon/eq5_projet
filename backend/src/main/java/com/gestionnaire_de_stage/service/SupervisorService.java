package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    public Supervisor create(Supervisor supervisor) throws SupervisorAlreadyExistsException {
        Assert.isTrue(supervisor != null, "Superviseur est null");
        if (isNotValid(supervisor)) {
            throw new SupervisorAlreadyExistsException();
        }
        return supervisorRepository.save(supervisor);
    }


    public Optional<Supervisor> getOneByID(Long aLong) {
        if (aLong != null && supervisorRepository.existsById(aLong)) {
            return Optional.of(supervisorRepository.getById(aLong));
        }
        return Optional.empty();
    }


    public List<Supervisor> getAll() {
        return supervisorRepository.findAll();
    }

    public Optional<Supervisor> update(Supervisor supervisor, Long aLong) throws ValidationException {
        if (aLong != null && supervisorRepository.existsById(aLong) && supervisor != null) {
            supervisor.setId(aLong);
            return Optional.of(supervisorRepository.save(supervisor));
        }
        return Optional.empty();
    }

    public Optional<Supervisor> getOneByEmailAndPassword(String email, String password) {
        if (supervisorRepository.existsByEmailAndPassword(email, password)) {
            return Optional.of(supervisorRepository.findSupervisorByEmailAndPassword(email, password));
        }
        return Optional.empty();
    }

    public boolean deleteByID(Long aLong) {
        if (aLong != null && supervisorRepository.existsById(aLong)) {
            supervisorRepository.deleteById(aLong);
            return true;
        }
        return false;
    }

    private boolean isNotValid(Supervisor supervisor) {
        return supervisor.getEmail() != null &&
                supervisorRepository.existsByEmail(supervisor.getEmail());
    }
}
