package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Gestation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestionnaireRepository extends JpaRepository<Gestation, Long> {
}
