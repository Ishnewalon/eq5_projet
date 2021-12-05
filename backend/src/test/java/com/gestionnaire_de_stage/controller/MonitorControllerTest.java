package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
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

    private final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MonitorService monitorService;

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
        when(monitorService.create(any())).thenThrow(new IllegalArgumentException("Le courriel ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/monitor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyMonitor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel ne peut pas être vide");
    }

    @Test
    public void monitorSignupTest_withInvalidEntries() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorService.create(any())).thenThrow(new MonitorAlreadyExistsException("Un compte existe déjà pour ce moniteur"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/monitor/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyMonitor))).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Un compte existe déjà pour ce moniteur");
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
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenThrow(new IllegalArgumentException("Courriel et mot de passe ne peuvent pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel et mot de passe ne peuvent pas être vide");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenThrow(new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalid"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel ou mot de passe invalid");
    }

    @Test
    public void testCheckEmailValidty() throws Exception {
        when(monitorService.isEmailInvalid(any())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/monitor/email/{email}", "potato@mail.com")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("false");
    }

    @Test
    public void testChangePassword() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        String newPassword = "newPassword";
        when(monitorService.changePassword(any(), any())).thenReturn(dummyMonitor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/monitor/change_password/{id}", dummyMonitor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Mot de passe changé avec succès");
    }

    @Test
    public void testChangePassword_withInvalidId() throws Exception {
        String newPassword = "newPassword";
        when(monitorService.changePassword(any(), any())).thenThrow(new IllegalArgumentException("Id invalide"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/monitor/change_password/{id}", 123412L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Id invalide");
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setEmail("potato@mail.com");
        dummyMonitor.setPassword("secretPasswordShhhh");
        dummyMonitor.setFirstName("toto");
        dummyMonitor.setLastName("tata");
        dummyMonitor.setDepartment("Informatique");
        return dummyMonitor;
    }
}
