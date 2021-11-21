package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.model.PasswordResetToken;
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

}
