package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.StageAlreadyExistsException;
import com.gestionnaire_de_stage.exception.StageDoesNotExistException;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.repository.StageRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class StageService {

    private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public Stage create(Stage stage) {
        Assert.isTrue(stage != null, "Le stage ne peut pas être null");
        return stageRepository.save(stage);
    }

    public Stage getStageByStudentMatricule(String matricule) {
        Assert.isTrue(matricule != null, "La matricule ne peut pas être null");
        return stageRepository.getStageByContractStudentMatricule(matricule);
    }

    public Stage addEvalMilieuStage(Stage stage, ByteArrayOutputStream baos) throws StageDoesNotExistException {
        Assert.isTrue(stage != null, "Le stage ne peut pas être null");
        Stage newStage = create(stage);
        if (isNotValid(stage)) {
            throw new StageDoesNotExistException();
        }
        stage.setEvalMilieuStage(baos.toByteArray());
        return stageRepository.save(stage);
    }

    private boolean isNotValid(Stage stage) {
        return !stageRepository.existsById(stage.getId());
    }
}
