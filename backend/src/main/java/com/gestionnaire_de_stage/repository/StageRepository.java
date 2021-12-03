package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {

    Stage getStageByContractStudentMatricule(String matricule);

    boolean existsByContract_StudentMatriculeAndEvalMilieuStageNotNull(String matricule);

    boolean existsByContract_StudentMatriculeAndEvalMilieuStageNull(String matricule);

    List<Stage> getAllByEvalStagiaireNotNullAndContract_Monitor_Id(Long idMonitor);

    Stage getByContract_StudentEmail(String email);

    boolean existsByContract_StudentEmail(String email);

    List<Stage> getAllByEvalMilieuStageIsNull();

    List<Stage> getAllByEvalStagiaireIsNull();

    boolean existsByContract_StudentEmailAndEvalStagiaireNotNull(String email);

    List<Stage> getAllByEvalMilieuStageNotNullAndContract_Student_Supervisor_Id(Long idSupervisor);
}
