package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
}
