package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByDateDebutBetweenOrDateFinBetween(LocalDate dateDebut, LocalDate dateDebut2, LocalDate dateFin, LocalDate dateFin2);
}
