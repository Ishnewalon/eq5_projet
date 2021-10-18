package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
    Supervisor findSupervisorByEmailAndPassword(String email, String password);
}
