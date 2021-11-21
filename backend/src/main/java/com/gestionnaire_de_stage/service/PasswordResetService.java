package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.PasswordResetToken;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.model.User;
import com.gestionnaire_de_stage.repository.PasswordResetTokenRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final MonitorService monitorService;
    private final SupervisorService supervisorService;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, MonitorService monitorService, SupervisorService supervisorService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.monitorService = monitorService;
        this.supervisorService = supervisorService;
    }

    public PasswordResetToken forgotPasswordMonitor(String email) throws EmailDoesNotExistException {
        Monitor monitor = monitorService.getOneByEmail(email);

        return forgotPassword(monitor);
    }

    private PasswordResetToken forgotPassword(User user) {
        Assert.notNull(user, "Un utilisateur est nécessaire afin de créé un token de récupération de mot de passe");
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken forgotPasswordSupervisor(String email) throws EmailDoesNotExistException {
        Supervisor supervisor = supervisorService.getOneByEmail(email);
        return forgotPassword(supervisor);
    }
}
