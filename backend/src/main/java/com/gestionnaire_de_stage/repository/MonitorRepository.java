package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    boolean existsByEmail(String email);


}
