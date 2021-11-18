package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

    Stage getStageByContractStudentMatricule(String matricule);

    boolean existsByContract_StudentMatriculeAndEvalMilieuStageNotNull(String matricule);

    boolean existsByContract_StudentMatriculeAndEvalMilieuStageNull(String matricule);

    Stage getByContract_StudentEmail(String email);

    boolean existsByContract_StudentEmail(String email);
}
