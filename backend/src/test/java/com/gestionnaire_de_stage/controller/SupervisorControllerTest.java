package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.AssignDto;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import com.gestionnaire_de_stage.service.StudentService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@WebMvcTest(SupervisorController.class)
public class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SupervisorService supervisorService;

    @MockBean
    StudentService studentService;

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
        when(supervisorService.create(any())).thenThrow(new IllegalArgumentException("Le courriel ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummySupervisor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel ne peut pas être vide");
    }

    @Test
    public void testSupervisorSignUp_withInvalidSupervisor() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorService.create(any())).thenThrow(new SupervisorAlreadyExistsException("Un compte existe déjà pour ce superviseur"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummySupervisor)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Un compte existe déjà pour ce superviseur");
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
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenThrow(new IllegalArgumentException("Courriel et mot de passe ne peuvent pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel et mot de passe ne peuvent pas être vide");
    }

    @Test
    public void testAssign() throws Exception {
        AssignDto assignDto = new AssignDto(1L, 2L);
        when(studentService.assign(any(), any())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/assign/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(assignDto)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Affectation fait!");
    }

    @Test
    public void testAssign_withInvalidSupervisorAndStudent() throws Exception {
        AssignDto assignDto = new AssignDto(1L, 2L);
        when(supervisorService.getOneByID(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/assign/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(assignDto)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de superviseur associé à cet identifiant");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "sinl@gmail.com";
        String password = "weightofworld";
        when(supervisorService.getOneByEmailAndPassword(any(), any())).thenThrow(new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/supervisor/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel ou mot de passe invalide");
    }

    @Test
    public void testGetAllSupervisor() throws Exception {
        List<Supervisor> list = Arrays.asList(new Supervisor(), new Supervisor());
        when(supervisorService.getAll()).thenReturn(Arrays.asList(new Supervisor(), new Supervisor()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Supervisor>>() {
                })).isEqualTo(list);

    }

    @Test
    public void testGetAllStudentsStatus_withValidEntries() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        List<OfferApplication> dummyOfferAppList = getDummyOfferAppList();
        when(supervisorService.getStudentsStatus(any())).thenReturn(dummyOfferAppList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/students_status/" + dummySupervisor.getId() )
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferApplication> actualOfferAppList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualOfferAppList.size()).isEqualTo(dummyOfferAppList.size());
    }

    @Test
    public void testGetAllStudentsStatus_withNullSupervisorId() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorService.getStudentsStatus(any())).thenThrow(new IllegalArgumentException("L'identifiant ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/students_status/" + dummySupervisor.getId() )
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant ne peut pas être vide");
    }

    @Test
    public void testGetAllStudentsStatus_withInvalidSupervisorId() throws Exception {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorService.getStudentsStatus(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/students_status/" + dummySupervisor.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de superviseur associé à cet identifiant");
    }

    @Test
    public void testCheckMatricule() throws Exception {
        when(studentService.isMatriculeValid(any())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/supervisor/matricule/{matricule}", "12345")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("true");
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

    private List<OfferApplication> getDummyOfferAppList() {
        List<OfferApplication> dummyOfferAppList = new ArrayList<>();
        OfferApplication dummyOfferApp1 = new OfferApplication();
        dummyOfferApp1.setId(1L);
        dummyOfferApp1.setOffer(new Offer());
        dummyOfferApp1.setCurriculum(new Curriculum());
        dummyOfferApp1.setStatus(Status.STAGE_TROUVE);
        dummyOfferAppList.add(dummyOfferApp1);

        OfferApplication dummyOfferApp2 = new OfferApplication();
        dummyOfferApp2.setId(1L);
        dummyOfferApp2.setOffer(new Offer());
        dummyOfferApp2.setCurriculum(new Curriculum());
        dummyOfferApp2.setStatus(Status.STAGE_REFUSE);
        dummyOfferAppList.add(dummyOfferApp2);

        OfferApplication dummyOfferApp3 = new OfferApplication();
        dummyOfferApp3.setId(1L);
        dummyOfferApp3.setOffer(new Offer());
        dummyOfferApp3.setCurriculum(new Curriculum());
        dummyOfferApp3.setStatus(Status.EN_ATTENTE_REPONSE);
        dummyOfferAppList.add(dummyOfferApp3);

        return dummyOfferAppList;
    }
}
