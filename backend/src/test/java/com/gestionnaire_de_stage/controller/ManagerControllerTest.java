package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import com.gestionnaire_de_stage.service.ManagerService;
import org.junit.jupiter.api.*;
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
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private ManagerRepository managerRepository;


    @Test
    public void testManagerLogin_withValidEntries() throws Exception {
        Manager manager = getDummyManager();
        String email = "oussamakably@gmail.com";
        String password = "Test1234";
        when(managerService.getOneByEmailAndPassword(any(),any())).thenReturn(manager);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/manager/" + email + "/" + password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actualSupervisor = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString()
                , Manager.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualSupervisor.getLastName()).isEqualTo("Kably");
    }

    @Test
    public void testManagerLogin_withNullEntries() throws Exception {
        String email = null;
        String password = null;
        when(managerService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        //noinspection ConstantConditions
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/manager/" + email + "/" + password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel et le mot de passe ne peuvent pas etre null");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "qerw@qwetq.com";
        String password = "qwreqwer";
        when(managerService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/manager/" + email + "/" + password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }
}
