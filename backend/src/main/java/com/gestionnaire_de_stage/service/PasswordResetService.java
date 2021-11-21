package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.PasswordResetTokenRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final MonitorService monitorService;
    private final SupervisorService supervisorService;
    private final StudentService studentService;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, MonitorService monitorService, SupervisorService supervisorService, StudentService studentService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.monitorService = monitorService;
        this.supervisorService = supervisorService;
        this.studentService = studentService;
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

    public PasswordResetToken forgotPasswordStudent(String email) throws EmailDoesNotExistException {
        Student student = studentService.getOneByEmail(email);
        return forgotPassword(student);
    }
}
