package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
