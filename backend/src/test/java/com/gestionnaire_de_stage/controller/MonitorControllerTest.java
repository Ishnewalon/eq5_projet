package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import com.gestionnaire_de_stage.service.MonitorService;
import com.gestionnaire_de_stage.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MonitorController.class)
public class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitorService monitorService;

    @MockBean
    private MonitorRepository monitorRepository;

    @MockBean
    private StudentService studentService;

    @Test
    public void monitorSignupTest_withValidEntries() throws Exception {
        Monitor monitor = getMonitor();
        when(monitorService.create(any())).thenReturn(monitor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(monitor))).andReturn();

        var actualMonitor = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Monitor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualMonitor).isEqualTo(monitor);
    }

    @Test
    public void monitorSignupTest_withNullEntries() throws Exception {
        Monitor monitor = getMonitor();
        when(monitorService.create(any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(monitor))).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel ne peut pas etre null");
    }

    @Test
    public void monitorSignupTest_withInvalidEntries() throws Exception {
        Monitor monitor = getMonitor();
        when(monitorService.create(any())).thenThrow(MonitorAlreadyExistsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(monitor))).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Ce courriel existe deja!");
    }
    
    @Test
    public void monitorLoginTest_withValidEntries() throws Exception {
        Monitor monitor = getMonitor();
        String email = "potato@mail.com";
        String password = "secretPasswordShhhh";
        when(monitorService.getOneByEmailAndPassword(any(),any())).thenReturn(monitor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actualMonitor = new ObjectMapper()
                .readValue(mvcResult.getResponse().getContentAsString(), Monitor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(actualMonitor.getLastName()).isEqualTo("tata");
    }

    @Test
    public void monitorLoginTest_withNullEntries() throws Exception {
        String email = "potato@mail.com";
        String password = "secretPasswordShhhh";
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel et le mot de passe ne peuvent pas etre null");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(monitorService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/monitor/" + email + "/" + password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    private Monitor getMonitor() {
        Monitor monitor = new Monitor();
        monitor.setEmail("potato@mail.com");
        monitor.setPassword("secretPasswordShhhh");
        monitor.setFirstName("toto");
        monitor.setLastName("tata");
        monitor.setDepartment("Informatique");
        return monitor;
    }
}
