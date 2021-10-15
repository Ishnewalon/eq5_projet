package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class SupervisorService implements ICrudService<Supervisor, Long> {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    public Optional<Supervisor> create(Supervisor supervisor) throws ValidationException {
        if (supervisor != null) {
            return Optional.of(supervisorRepository.save(supervisor));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Supervisor> getOneByID(Long aLong) {
        if (aLong != null && supervisorRepository.existsById(aLong)) {
            return Optional.of(supervisorRepository.getById(aLong));
        }
        return Optional.empty();
    }

    @Override
    public List<Supervisor> getAll() {
        return supervisorRepository.findAll();
    }

    @Override
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

    @Override
    public boolean deleteByID(Long aLong) {
        if (aLong != null && supervisorRepository.existsById(aLong)) {
            supervisorRepository.deleteById(aLong);
            return true;
        }
        return false;
    }
}
