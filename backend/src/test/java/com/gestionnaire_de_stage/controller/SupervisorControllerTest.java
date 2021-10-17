package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        Supervisor supervisor = new Supervisor();
        supervisor.setId(1L);
        supervisor.setLastName("Singh");
        supervisor.setFirstName("Lohse");
        supervisor.setPhone("514-845-3234");
        supervisor.setEmail("sinl@gmail.com");
        supervisor.setPassword("weightofworld");
        supervisor.setDepartment("Informatique");
        supervisor.setMatricule("07485");

        when(supervisorService.create(any())).thenReturn(supervisor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/supervisor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(supervisor))).andReturn();

        var actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Supervisor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(supervisor);
    }

    @Test
    public void testSupervisorSignUp_withNullEntries() throws Exception {
        Supervisor supervisor = null;

       // when(supervisorService.create(supervisor)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/supervisor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(supervisor))).andReturn();
        Student actualStudent = null;
        try {
            actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        } catch (Exception ignored) {

        }

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actualStudent).isEqualTo(null);
    }

    @Test
    public void testSupervisorLogin_withValidEntries() throws Exception {
        Supervisor supervisor = supervisorLogin();
        String email = "sinl@gmail.com";
        String password = "weightofworld";
     //   when(supervisorService.getOneByEmailAndPassword(email, password)).thenReturn(Optional.of(supervisor));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/sinl@gmail.com/weightofworld")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actualSupervisor = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Supervisor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualSupervisor.getLastName()).isEqualTo("Singh");
    }

    @Test
    public void testSupervisorLogin_withNullEntries() throws Exception {
        String email = null;
        String password = null;
       // when(supervisorService.getOneByEmailAndPassword(email, password)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/null/null")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Student actualStudent = null;
        try {
            actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        } catch (Exception ignored) {

        }
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actualStudent).isEqualTo(null);
    }

    private Supervisor supervisorLogin() {
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
