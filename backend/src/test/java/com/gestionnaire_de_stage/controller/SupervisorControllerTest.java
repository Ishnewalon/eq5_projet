package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import com.gestionnaire_de_stage.service.SupervisorService;
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


@WebMvcTest(SupervisorController.class)
public class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SupervisorService supervisorService;

    @MockBean
    SupervisorRepository supervisorRepository;

    @Test
    public void testSupervisorSignUp_withValidEntries() throws Exception {
        Supervisor supervisor = getSupervisor();
        when(supervisorService.create(any())).thenReturn(supervisor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/supervisor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(supervisor))).andReturn();

        var actualSupervisor = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Supervisor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualSupervisor).isEqualTo(supervisor);
    }

    @Test
    public void testSupervisorSignUp_withNullSupervisor() throws Exception {
        Supervisor supervisor = getSupervisor();
        when(supervisorService.create(any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/supervisor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(supervisor))).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel ne peut pas etre null");
    }

    @Test
    public void testSupervisorSignUp_withInvalidSupervisor() throws Exception {
        Supervisor supervisor = getSupervisor();
        when(supervisorService.create(any())).thenThrow(SupervisorAlreadyExistsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/supervisor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(supervisor))).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Ce courriel existe deja!");
    }

    @Test
    public void testSupervisorLogin_withValidEntries() throws Exception {
        Supervisor supervisor = getSupervisor();
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(supervisorService.getOneByEmailAndPassword(any(),any())).thenReturn(supervisor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actualSupervisor = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Supervisor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualSupervisor.getLastName()).isEqualTo("Singh");
    }

    @Test
    public void testSupervisorLogin_withNullEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel et le mot de passe ne peuvent pas etre null");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    private Supervisor getSupervisor() {
        Supervisor supervisor = new Supervisor();
        supervisor.setId(1L);
        supervisor.setLastName("Singh");
        supervisor.setFirstName("Lohse");
        supervisor.setPhone("514-845-3234");
        supervisor.setEmail("sinl@gmail.com");
        supervisor.setPassword("weightofworld");
        supervisor.setDepartment("Informatique");
        supervisor.setMatricule("07485");
        return supervisor;
    }
}
