package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.PasswordResetTokenDto;
import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.UnusableTokenException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordResetServiceTest {

    @InjectMocks
    private PasswordResetService passwordResetService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private MonitorService monitorService;
    @Mock
    private SupervisorService supervisorService;
    @Mock
    private StudentService studentService;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void testForgotPassword_monitor_withValidEmail() throws DoesNotExistException {
        Monitor dummyMonitor = getDummyMonitor();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummyMonitor);
        when(monitorService.getOneByEmail(any())).thenReturn(dummyMonitor);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        PasswordResetToken actual = passwordResetService.forgotPasswordMonitor(dummyMonitor.getEmail());

        assertThat(actual.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(actual.getUser()).isEqualTo(dummyMonitor);
        assertThat(actual.getToken()).isNotNull();
    }

    @Test
    public void testForgotPassword_supervisor_withValidEmail() throws DoesNotExistException {
        Supervisor dummySupervisor = getDummySupervisor();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummySupervisor);
        when(supervisorService.getOneByEmail(any())).thenReturn(dummySupervisor);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        PasswordResetToken actual = passwordResetService.forgotPasswordSupervisor(dummySupervisor.getEmail());

        assertThat(actual.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(actual.getUser()).isEqualTo(dummySupervisor);
        assertThat(actual.getToken()).isNotNull();
    }

    @Test
    public void testForgotPassword_student_withValidEmail() throws DoesNotExistException {
        Student dummyStudent = getDummyStudent();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummyStudent);
        when(studentService.getOneByEmail(any())).thenReturn(dummyStudent);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        PasswordResetToken actual = passwordResetService.forgotPasswordStudent(dummyStudent.getEmail());

        assertThat(actual.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(actual.getUser()).isEqualTo(dummyStudent);
        assertThat(actual.getToken()).isNotNull();
    }

    @Test
    public void testResetPassword_monitor_valid() throws IdDoesNotExistException, DoesNotExistException, UnusableTokenException {
        Monitor dummyMonitor = getDummyMonitor();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummyMonitor);
        when(passwordResetTokenRepository.existsByToken(any())).thenReturn(true);
        when(passwordResetTokenRepository.getByToken(any())).thenReturn(dummyPasswordResetToken);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);
        when(monitorService.update(any())).thenReturn(dummyMonitor);

        Monitor actual = (Monitor) passwordResetService.resetPassword(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "testPassword"));

        assertThat(actual).isEqualTo(dummyMonitor);
    }

    @Test
    public void testResetPassword_supervisor_valid() throws IdDoesNotExistException, DoesNotExistException, UnusableTokenException {
        Supervisor dummySupervisor = getDummySupervisor();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummySupervisor);
        when(passwordResetTokenRepository.existsByToken(any())).thenReturn(true);
        when(passwordResetTokenRepository.getByToken(any())).thenReturn(dummyPasswordResetToken);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);
        when(supervisorService.update(any())).thenReturn(dummySupervisor);

        Supervisor actual = (Supervisor) passwordResetService.resetPassword(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "testPassword"));

        assertThat(actual).isEqualTo(dummySupervisor);
    }

    @Test
    public void testResetPassword_student_valid() throws IdDoesNotExistException, DoesNotExistException, UnusableTokenException {
        Student dummyStudent = getDummyStudent();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummyStudent);
        when(passwordResetTokenRepository.existsByToken(any())).thenReturn(true);
        when(passwordResetTokenRepository.getByToken(any())).thenReturn(dummyPasswordResetToken);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);
        when(studentService.update(any())).thenReturn(dummyStudent);

        Student actual = (Student) passwordResetService.resetPassword(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "testPassword"));

        assertThat(actual).isEqualTo(dummyStudent);
    }

    @Test
    public void testResetPassword_whenTokenDoesNotExist() {
        when(passwordResetTokenRepository.existsByToken(any())).thenReturn(false);

        assertThrows(DoesNotExistException.class,
                () -> passwordResetService.resetPassword(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "testPassword")),
                "Le token n'existe pas");
    }

    @Test
    public void testResetPassword_whenTokenUnusable() {
        when(passwordResetTokenRepository.existsByToken(any())).thenReturn(true);
        when(passwordResetTokenRepository.existsByTokenAndUnusableTrue(any())).thenReturn(true);

        assertThrows(UnusableTokenException.class,
                () -> passwordResetService.resetPassword(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "testPassword")),
                "Le token n'est plus utilisable");
    }

    private PasswordResetToken getDummyPasswordResetToken(User user) {
        PasswordResetToken dummyPasswordResetToken = new PasswordResetToken();
        dummyPasswordResetToken.setId(1L);
        dummyPasswordResetToken.setUser(user);
        dummyPasswordResetToken.setToken("testToken");
        return dummyPasswordResetToken;
    }


    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setLastName("toto");
        dummyMonitor.setFirstName("titi");
        dummyMonitor.setEmail("toto@gmail.com");
        dummyMonitor.setPassword("testPassword");
        return dummyMonitor;
    }

    private Supervisor getDummySupervisor() {
        Supervisor dummySupervisor = new Supervisor();
        dummySupervisor.setId(1L);
        dummySupervisor.setLastName("Keys");
        dummySupervisor.setFirstName("Harold");
        dummySupervisor.setEmail("keyh@gmail.com");
        dummySupervisor.setPassword("galaxy29");
        dummySupervisor.setDepartment("Comptabilité");
        dummySupervisor.setMatricule("04736");
        return dummySupervisor;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Candle");
        dummyStudent.setFirstName("Tea");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }
}
