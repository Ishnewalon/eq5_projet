package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.PasswordResetTokenDto;
import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.UnusableTokenException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.PasswordResetTokenRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.mail.username}")
    private String fromMail;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, MonitorService monitorService, SupervisorService supervisorService, StudentService studentService, JavaMailSender mailSender) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.monitorService = monitorService;
        this.supervisorService = supervisorService;
        this.studentService = studentService;
        this.mailSender = mailSender;
    }

    private PasswordResetToken forgotPassword(User user) {
        Assert.notNull(user, "Un utilisateur est nécessaire afin de créer un jeton de récupération de mot de passe");
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        sendEmail(user.getEmail(), passwordResetToken.getToken());
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken forgotPasswordMonitor(String email) throws DoesNotExistException {
        Monitor monitor = monitorService.getOneByEmail(email);
        return forgotPassword(monitor);
    }

    public PasswordResetToken forgotPasswordSupervisor(String email) throws DoesNotExistException {
        Supervisor supervisor = supervisorService.getOneByEmail(email);
        return forgotPassword(supervisor);
    }

    public PasswordResetToken forgotPasswordStudent(String email) throws DoesNotExistException {
        Student student = studentService.getOneByEmail(email);
        return forgotPassword(student);
    }

    public User resetPassword(PasswordResetTokenDto passwordResetTokenDto) throws DoesNotExistException, IdDoesNotExistException, UnusableTokenException {
        Assert.notNull(passwordResetTokenDto, "Un jeton et un mot de passe sont nécessaires pour une modification de mot de passe");
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
        Assert.notNull(token, "Un jeton est nécessaire pour une modification de mot de passe");
        if (doesNotExists(token))
            throw new DoesNotExistException("Le jeton n'existe pas");
        if (isTokenUnusable(token))
            throw new UnusableTokenException("Le jeton n'est plus utilisable");

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

    private void sendEmail(String mailTo,  String token) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(this.fromMail);
        mail.setSubject("Mot de passe oublié");
        mail.setText("Veuillez cliquer sur le lien suivant pour réinitialiser votre mot de passe : http://localhost:3000/reset_password/" + token);
        mail.setTo(mailTo);

        this.mailSender.send(mail);
    }
}
