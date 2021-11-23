package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EvaluationAlreadyFilledException;
import com.gestionnaire_de_stage.exception.StageDoesNotExistException;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.repository.StageRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class StageService {

    private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public Stage create(Stage stage, String matricule) throws EvaluationAlreadyFilledException {
        Assert.isTrue(stage != null, "Le stage ne peut pas être null");
        if (isAlreadyCreated(matricule)) {
            throw new EvaluationAlreadyFilledException("L'évalutation de cet étudiant a déjà été remplie");
        }
        if (isAlreadyCreatedButNoEvalMilieu(matricule)) {
            stage = stageRepository.getStageByContractStudentMatricule(matricule);
        }
        return stageRepository.save(stage);
    }

    public Stage addEvalMilieuStage(Stage stage, ByteArrayOutputStream baos) throws StageDoesNotExistException {
        Assert.isTrue(stage != null, "Le stage ne peut pas être null");
        if (isNotValid(stage)) {
            throw new StageDoesNotExistException("Il n'y a pas de stage pour cette étudiant");
        }
        stage.setEvalMilieuStage(baos.toByteArray());
        return stageRepository.save(stage);
    }

    public Stage getStageByStudentEmail(String email) throws StageDoesNotExistException, EvaluationAlreadyFilledException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être null");
        if (isNotAlreadyCreatedEmail(email)) {
            throw new StageDoesNotExistException("Il n'y a pas de stage pour cette étudiant");
        }
        if (isEvalStagiaireFilled(email)) {
            throw new EvaluationAlreadyFilledException("L'évalutation de ce stagiaire a déjà été remplie");
        }
        return stageRepository.getByContract_StudentEmail(email);
    }

    public Stage addEvalStagiaire(Stage stage, ByteArrayOutputStream baos) throws StageDoesNotExistException {
        Assert.isTrue(stage != null, "Le stage ne peut pas être null");
        if (isNotValid(stage)) {
            throw new StageDoesNotExistException("Il n'y a pas de stage pour cette étudiant");
        }
        stage.setEvalStagiaire(baos.toByteArray());
        return stageRepository.save(stage);
    }

    public List<Stage> getAllWithNoEvalMilieu() {
        return stageRepository.getAllByEvalMilieuStageIsNull();
    }

    public List<Stage> getAllWithNoEvalStagiaire() {
        return stageRepository.getAllByEvalStagiaireIsNull();
    }

    private boolean isNotValid(Stage stage) {
        return !stageRepository.existsById(stage.getId());
    }

    private boolean isAlreadyCreatedButNoEvalMilieu(String matricule) {
        return stageRepository.existsByContract_StudentMatriculeAndEvalMilieuStageNull(matricule);
    }

    private boolean isAlreadyCreated(String matricule) {
        return stageRepository.existsByContract_StudentMatriculeAndEvalMilieuStageNotNull(matricule);
    }

    private boolean isNotAlreadyCreatedEmail(String email) {
        return !stageRepository.existsByContract_StudentEmail(email);
    }

    private boolean isEvalStagiaireFilled(String email) {
        return stageRepository.existsByContract_StudentEmailAndEvalStagiaireNotNull(email);
    }
}
