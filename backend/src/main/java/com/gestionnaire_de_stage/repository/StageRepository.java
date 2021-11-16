package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

    boolean existsByContract_StudentMatricule(String matricule);

    Stage getByContract_StudentEmail(String email);

    boolean existsByContract_StudentEmail(String email);
}
