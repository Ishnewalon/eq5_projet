package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    public Monitor signup(Monitor monitor) {
        return monitorRepository.save(monitor);
    }
}
