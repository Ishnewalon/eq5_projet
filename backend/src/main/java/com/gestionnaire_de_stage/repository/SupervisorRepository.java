package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    boolean existsByEmailAndPassword(String email, String password);
    Supervisor findSupervisorByEmailAndPassword(String email, String password);
}
