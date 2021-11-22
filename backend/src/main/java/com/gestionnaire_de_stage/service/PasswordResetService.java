package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.PasswordResetTokenDto;
import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.UnusableTokenException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.PasswordResetTokenRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final MonitorService monitorService;
    private final SupervisorService supervisorService;
    private final StudentService studentService;
    private final JavaMailSender mailSender;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, MonitorService monitorService, SupervisorService supervisorService, StudentService studentService, JavaMailSender mailSender) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.monitorService = monitorService;
        this.supervisorService = supervisorService;
        this.studentService = studentService;
        this.mailSender = mailSender;
    }

    private PasswordResetToken forgotPassword(User user) {
        Assert.notNull(user, "Un utilisateur est nécessaire afin de créé un token de récupération de mot de passe");
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        String message = "Veuillez cliquer sur le lien suivant pour réinitialiser votre mot de passe : http://localhost:3000/reset_password/" + passwordResetToken.getToken();
        sendEmail(user.getEmail(), "Mot de passe oublié", message);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken forgotPasswordMonitor(String email) throws EmailDoesNotExistException {
        Monitor monitor = monitorService.getOneByEmail(email);
        return forgotPassword(monitor);
    }

    public PasswordResetToken forgotPasswordSupervisor(String email) throws EmailDoesNotExistException {
        Supervisor supervisor = supervisorService.getOneByEmail(email);
        return forgotPassword(supervisor);
    }

    public PasswordResetToken forgotPasswordStudent(String email) throws EmailDoesNotExistException {
        Student student = studentService.getOneByEmail(email);
        return forgotPassword(student);
    }

    public User resetPassword(PasswordResetTokenDto passwordResetTokenDto) throws DoesNotExistException, IdDoesNotExistException, UnusableTokenException {
        Assert.notNull(passwordResetTokenDto, "Un token et un mot de passe est nécessaire pour une modification de mot de passe");
        PasswordResetToken passwordResetToken = getOneByToken(passwordResetTokenDto.getToken());

        User user = updateUserPassword(passwordResetTokenDto.getPassword(), passwordResetToken.getUser());

        setTokenUnusable(passwordResetToken);

        return user;
    }

    private User updateUserPassword(String password, User user) throws IdDoesNotExistException {
        Assert.notNull(password, "Un mot de passe est nécessaire pour une modification de mot de passe");
        user.setPassword(password);

        if (user instanceof Monitor)
            user = monitorService.update((Monitor) user);
        else if (user instanceof Supervisor)
            user = supervisorService.update((Supervisor) user);
        else if (user instanceof Student)
            user = studentService.update((Student) user);
        return user;
    }


    private PasswordResetToken getOneByToken(String token) throws DoesNotExistException, UnusableTokenException {
        Assert.notNull(token, "Un token est nécessaire pour une modification de mot de passe");
        if (doesNotExists(token))
            throw new DoesNotExistException("Le token n'existe pas");
        if (isTokenUnusable(token))
            throw new UnusableTokenException("Le token n'est plus utilisable");

        return passwordResetTokenRepository.getByToken(token);
    }

    private boolean isTokenUnusable(String token) {
        return passwordResetTokenRepository.existsByTokenAndUnusableTrue(token);
    }

    private void setTokenUnusable(PasswordResetToken passwordResetToken) {
        passwordResetToken.setUnusable();
        passwordResetTokenRepository.save(passwordResetToken);
    }

    private boolean doesNotExists(String token) {
        return !passwordResetTokenRepository.existsByToken(token);
    }

    private void sendEmail(String mailTo, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("jisos.eq5@gmail.com");
        mail.setSubject(subject);
        mail.setText(body);
        mail.setTo(mailTo);


        this.mailSender.send(mail);
    }
}
