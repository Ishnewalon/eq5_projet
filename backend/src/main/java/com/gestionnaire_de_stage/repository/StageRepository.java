package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
}
