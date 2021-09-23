package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
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
import static org.mockito.Mockito.when;

//@WebMvcTest(SupervisorController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SupervisorService supervisorService;

    @Test
    public void testSupervisorSignUp_withValidEntries() throws Exception {
        Supervisor supervisor = new Supervisor();
        supervisor.setId(1L);
        supervisor.setName("Singh");
        supervisor.setFirstName("Lohse");
        supervisor.setNumTel("514-845-3234");
        supervisor.setEmail("sinl@gmail.com");
        supervisor.setPassword("weightofworld");
        supervisor.setDepartment("Informatique");
        supervisor.setMatricule("07485");

        when(supervisorService.create(supervisor)).thenReturn(Optional.of(supervisor));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/supervisor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(supervisor))).andReturn();

        var actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Supervisor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(supervisor);
    }
}
