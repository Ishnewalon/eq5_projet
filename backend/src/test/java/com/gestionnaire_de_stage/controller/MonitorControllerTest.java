package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.PasswordResetToken;
import com.gestionnaire_de_stage.service.MonitorService;
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

@WebMvcTest(MonitorController.class)
public class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitorService monitorService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void monitorSignupTest_withValidEntries() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorService.create(any())).thenReturn(dummyMonitor);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/monitor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyMonitor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Monitor actualMonitor = MAPPER.readValue(response.getContentAsString(), Monitor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualMonitor).isEqualTo(dummyMonitor);
    }

    @Test
    public void monitorSignupTest_withNullEntries() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorService.create(any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/monitor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyMonitor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le courriel ne peut pas être null");
    }

    @Test
    public void monitorSignupTest_withInvalidEntries() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorService.create(any())).thenThrow(MonitorAlreadyExistsException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/monitor/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyMonitor))).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Ce courriel existe déjà!");
    }

    @Test
    public void monitorLoginTest_withValidEntries() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        String email = "potato@mail.com";
        String password = "secretPasswordShhhh";
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenReturn(dummyMonitor);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Monitor actualMonitor = MAPPER.readValue(response.getContentAsString(), Monitor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualMonitor.getLastName()).isEqualTo("tata");
    }

    @Test
    public void monitorLoginTest_withNullEntries() throws Exception {
        String email = "potato@mail.com";
        String password = "secretPasswordShhhh";
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le courriel et le mot de passe ne peuvent pas être null");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    @Test
    public void testForgotPassword() throws Exception {
        String email = "monitor@email.com";

        when(monitorService.forgotPassword(any())).thenReturn(new PasswordResetToken());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/monitor/forgot_password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(email))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Un email vous a été envoyé pour réinitialiser votre mot de passe");
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setEmail("potato@mail.com");
        dummyMonitor.setPassword("secretPasswordShhhh");
        dummyMonitor.setFirstName("toto");
        dummyMonitor.setLastName("tata");
        dummyMonitor.setDepartment("Informatique");
        return dummyMonitor;
    }
}
