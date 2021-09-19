package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorService implements ICrudService<Monitor, Long> {

    @Autowired
    private MonitorRepository monitorRepository;


    @Override
    public Monitor create(Monitor monitor) {
        return null;
    }

    @Override
    public Monitor getOneByID(Long aLong) {
        return null;
    }

    @Override
    public List<Monitor> getAll() {
        return null;
    }

    @Override
    public Monitor update(Monitor monitor, Long aLong) {
        return null;
    }

    @Override
    public boolean deleteByID(Long aLong) {
        return false;
    }
}
