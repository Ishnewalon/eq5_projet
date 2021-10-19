package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import com.gestionnaire_de_stage.service.SupervisorService;
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


@WebMvcTest(SupervisorController.class)
public class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SupervisorService supervisorService;

    @MockBean
    SupervisorRepository supervisorRepository;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSupervisorSignUp_withValidEntries() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorService.create(any())).thenReturn(dummySupervisor);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummySupervisor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Supervisor actualSupervisor = MAPPER.readValue(response.getContentAsString(), Supervisor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualSupervisor).isEqualTo(dummySupervisor);
    }

    @Test
    public void testSupervisorSignUp_withNullEntries() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorService.create(any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummySupervisor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le courriel ne peut pas être null");
    }

    @Test
    public void testSupervisorSignUp_withInvalidSupervisor() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorService.create(any())).thenThrow(SupervisorAlreadyExistsException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummySupervisor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Ce courriel existe déjà!");
    }

    @Test
    public void testSupervisorLogin_withValidEntries() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenReturn(dummySupervisor);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Supervisor actualSupervisor = MAPPER.readValue(response.getContentAsString(), Supervisor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualSupervisor.getLastName()).isEqualTo("Singh");
    }

    @Test
    public void testSupervisorLogin_withNullEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
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
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    private Supervisor getDummySupervisor() {
        Supervisor dummySupervisor = new Supervisor();
        dummySupervisor.setId(1L);
        dummySupervisor.setLastName("Singh");
        dummySupervisor.setFirstName("Lohse");
        dummySupervisor.setPhone("514-845-3234");
        dummySupervisor.setEmail("sinl@gmail.com");
        dummySupervisor.setPassword("weightofworld");
        dummySupervisor.setDepartment("Informatique");
        dummySupervisor.setMatricule("07485");
        return dummySupervisor;
    }
}
