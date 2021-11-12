package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.repository.StageRepository;
import org.springframework.stereotype.Service;

@Service
public class StageService {

    private final StageRepository stageRepository;


    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }
}
