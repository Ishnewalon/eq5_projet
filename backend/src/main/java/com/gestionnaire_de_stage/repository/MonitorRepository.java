package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Monitor findMonitorByEmailAndPassword(String email, String password);


}
