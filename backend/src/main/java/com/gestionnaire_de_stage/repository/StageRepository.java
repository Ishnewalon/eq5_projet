package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

    Stage getStageByContractStudentMatricule(String matricule);

    boolean existsByContractStudentMatricule(String matricule);

    Stage getByContractStudent_Email(String email);
}
