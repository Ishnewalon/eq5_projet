package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findManagerByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);
}
