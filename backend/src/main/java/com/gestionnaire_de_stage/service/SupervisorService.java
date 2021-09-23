package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class SupervisorService implements ICrudService<Supervisor, Long> {

    @Autowired
    SupervisorRepository supervisorRepository;

    @Override
    public Optional<Supervisor> create(Supervisor supervisor) throws ValidationException {
        if (supervisor != null) {
            return Optional.of(supervisorRepository.save(supervisor));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Supervisor> getOneByID(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Supervisor> getAll() {
        return null;
    }

    @Override
    public Optional<Supervisor> update(Supervisor supervisor, Long aLong) throws ValidationException {
        return Optional.empty();
    }

    @Override
    public Optional<Supervisor> getOneByEmailAndPassword(String email, String password) {
        return Optional.empty();
    }

    @Override
    public boolean deleteByID(Long aLong) {
        return false;
    }
}
