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
        assertThat(response.getContentAsString()).contains("Assignement fait!");
    }

    @Test
    public void testAssign_withInvalidSupervisorAndStudent() throws Exception {
        AssignDto assignDto = new AssignDto(1L, 2L);
        when(supervisorService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/supervisor/assign/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(assignDto)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Inexistant");
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
        when(supervisorService.getOneByID(any())).thenReturn(dummySupervisor);
        when(supervisorService.getStudentsStatus(any())).thenReturn(dummyOfferAppList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/supervisor/getStudentsStatus/" + dummySupervisor.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferApplication> actualOfferAppList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualOfferAppList.size()).isEqualTo(dummyOfferAppList.size());
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
