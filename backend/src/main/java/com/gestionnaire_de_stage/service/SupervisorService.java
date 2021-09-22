package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Supervisor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupervisorService implements ICrudService<Supervisor, Long> {
    @Override
    public Optional<Supervisor> create(Supervisor supervisor) throws Exception {
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
    public Optional<Supervisor> update(Supervisor supervisor, Long aLong) throws Exception {
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
