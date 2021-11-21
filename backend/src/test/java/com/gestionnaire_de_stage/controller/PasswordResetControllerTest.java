package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.PasswordResetTokenDto;
import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.PasswordResetToken;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.service.PasswordResetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(PasswordResetController.class)
public class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordResetService passwordResetService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testForgotPassword_monitor() throws Exception {
        String email = "monitor@email.com";
        when(passwordResetService.forgotPasswordMonitor(any())).thenReturn(new PasswordResetToken());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/monitor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Un email vous a été envoyé pour réinitialiser votre mot de passe");
    }

    @Test
    public void testForgotPassword_monitor_whenEmailDoesNotExist() throws Exception {
        String email = "monitor@email.com";
        when(passwordResetService.forgotPasswordMonitor(any())).thenThrow(new EmailDoesNotExistException("Ce email n'existe pas"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/monitor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Ce email n'existe pas");
    }

    @Test
    public void testForgotPassword_supervisor() throws Exception {
        String email = "supervisor@email.com";
        when(passwordResetService.forgotPasswordSupervisor(any())).thenReturn(new PasswordResetToken());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/supervisor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Un email vous a été envoyé pour réinitialiser votre mot de passe");
    }

    @Test
    public void testForgotPassword_supervisor_whenEmailDoesNotExist() throws Exception {
        String email = "supervisor@email.com";
        when(passwordResetService.forgotPasswordSupervisor(any())).thenThrow(new EmailDoesNotExistException("Ce email n'existe pas"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/supervisor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Ce email n'existe pas");
    }

    @Test
    public void testForgotPassword_student() throws Exception {
        String email = "student@email.com";
        when(passwordResetService.forgotPasswordStudent(any())).thenReturn(new PasswordResetToken());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Un email vous a été envoyé pour réinitialiser votre mot de passe");
    }

    @Test
    public void testForgotPassword_student_whenEmailDoesNotExist() throws Exception {
        String email = "student@email.com";
        when(passwordResetService.forgotPasswordStudent(any())).thenThrow(new EmailDoesNotExistException("Ce email n'existe pas"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Ce email n'existe pas");
    }


    @Test
    public void testResetPassword_monitor() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(passwordResetService.resetPassword(any())).thenReturn(dummyMonitor);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "newPassword"))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Votre mot de passe a été réinitialisé avec succès!");
    }

    @Test
    public void testResetPassword_monitor_withTokenNull() throws Exception {
        when(passwordResetService.resetPassword(any())).thenThrow(new IllegalArgumentException("Un token est nécessaire pour une modification de mot de passe"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new PasswordResetTokenDto(null, "newPassword"))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Un token est nécessaire pour une modification de mot de passe");
    }

    @Test
    public void testResetPassword_monitor_withPasswordNull() throws Exception {
        when(passwordResetService.resetPassword(any())).thenThrow(new IllegalArgumentException("Un nouveau mot de passe est nécessaire pour une modification de mot de passe"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", null))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Un nouveau mot de passe est nécessaire pour une modification de mot de passe");
    }

    @Test
    public void testResetPassword_monitor_withTokenNonExistant() throws Exception {
        when(passwordResetService.resetPassword(any())).thenThrow(new DoesNotExistException("Ce token n'existe pas"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/forgot_password/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new PasswordResetTokenDto("DIBWA213Nw_dW31Ad3DAO9WD213", "newPassword"))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Ce token n'existe pas");
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
