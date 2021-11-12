package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {
}
