package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void testForgotPassword_monitor_withValidEmail() throws EmailDoesNotExistException {
        Monitor dummyMonitor = getDummyMonitor();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummyMonitor);
        when(monitorService.getOneByEmail(any())).thenReturn(dummyMonitor);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);

        PasswordResetToken passwordResetToken = passwordResetService.forgotPasswordMonitor(dummyMonitor.getEmail());

        assertThat(passwordResetToken.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(passwordResetToken.getUser()).isEqualTo(dummyMonitor);
        assertThat(passwordResetToken.getToken()).isNotNull();
    }

    @Test
    public void testForgotPassword_supervisor_withValidEmail() throws EmailDoesNotExistException {
        Supervisor dummySupervisor = getDummySupervisor();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummySupervisor);
        when(supervisorService.getOneByEmail(any())).thenReturn(dummySupervisor);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);

        PasswordResetToken passwordResetToken = passwordResetService.forgotPasswordSupervisor(dummySupervisor.getEmail());

        assertThat(passwordResetToken.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(passwordResetToken.getUser()).isEqualTo(dummySupervisor);
        assertThat(passwordResetToken.getToken()).isNotNull();
    }

    @Test
    public void testForgotPassword_student_withValidEmail() throws EmailDoesNotExistException {
        Student dummyStudent = getDummyStudent();
        PasswordResetToken dummyPasswordResetToken = getDummyPasswordResetToken(dummyStudent);
        when(studentService.getOneByEmail(any())).thenReturn(dummyStudent);
        when(passwordResetTokenRepository.save(any())).thenReturn(dummyPasswordResetToken);

        PasswordResetToken passwordResetToken = passwordResetService.forgotPasswordStudent(dummyStudent.getEmail());

        assertThat(passwordResetToken.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(passwordResetToken.getUser()).isEqualTo(dummyStudent);
        assertThat(passwordResetToken.getToken()).isNotNull();
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
