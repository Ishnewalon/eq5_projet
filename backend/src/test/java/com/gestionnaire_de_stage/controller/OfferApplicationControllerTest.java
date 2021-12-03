package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.DateNotValidException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.exception.StudentHasNoCurriculumException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.service.OfferApplicationService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@WebMvcTest(OfferApplicationController.class)
class OfferApplicationControllerTest {

    private final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferApplicationService offerApplicationService;

    @Test
    public void testStudentApplyToOffer() throws Exception {
        when(offerApplicationService.create(any(), any())).thenReturn(getDummyOfferApp());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(CREATED.value());
        assertThat(response.getContentAsString()).contains("Candidature envoyé!");
    }

    @Test
    public void testStudentApplyToOfferAgain() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new StudentAlreadyAppliedToOfferException("Vous avez déjà postulé sur cette offre"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Vous avez déjà postulé sur cette offre");
    }

    @Test
    public void testStudentApplyToOffer_withOfferNonExistant() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'offre associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'offre associé à cet identifiant");
    }

    @Test
    public void testStudentApplyToOffer_withStudentNonExistant() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'étudiant associé à cet identifiant");
    }

    @Test
    public void testStudentApplyToOffer_withDTOWithNoOfferId() throws Exception {
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdOffer(null);
        when(offerApplicationService.create(any(), any())).thenThrow(new IllegalArgumentException("L'identifiant de l'offre ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferAppDto)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'offre ne peut pas être vide");
    }

    @Test
    public void testStudentApplyToOffer_withDTOWithNoStudentId() throws Exception {
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdStudent(null);
        when(offerApplicationService.create(any(), any())).thenThrow(new IllegalArgumentException("Le identifiant du curriculum ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferAppDto)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le identifiant du curriculum ne peut pas être vide");
    }

    @Test
    public void testStudentApplyToOffer_withCvInvalid() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new StudentHasNoCurriculumException("Vous devez avoir un curriculum principal valide avant de postuler"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Vous devez avoir un curriculum principal valide avant de postuler");
    }

    @Test
    public void testGetOffersApplicationStageTrouver() throws Exception {
        List<Student> studentList = getDummyStudentList();
        when(offerApplicationService.getOffersApplicationsStageTrouver()).thenReturn(studentList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/supervisor")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Student> actualList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualList).isNotEmpty();
    }

    @Test
    public void testViewStudentsAppliedOffer_withValidEntries() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        String email = "rolling@email.com";
        when(offerApplicationService.getAllByOfferCreatorEmail(any()))
                .thenReturn(offerApplicationsList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/{}", email)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferApplication> actualList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualList.size()).isEqualTo(3);
    }

    @Test
    public void testViewStudentsAppliedOffer_withNullEmail() throws Exception {
        String email = "";
        when(offerApplicationService.getAllByOfferCreatorEmail(any()))
                .thenThrow(new IllegalArgumentException("Le courriel ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/{}", email)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel ne peut pas être vide");
    }

    @Test
    void testSetInterviewDate_withValidIDs() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        OfferApplication offerApplication = getDummyOfferApp();
        offerApplication.setInterviewDate(LocalDateTime.now());
        when(offerApplicationService.setInterviewDate(any(), any()))
                .thenReturn(offerApplication);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/setdate/" + offerApplication.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final OfferApplication actual = MAPPER.readValue(response.getContentAsString(), OfferApplication.class);
        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(actual.getInterviewDate()).isEqualTo(offerApplication.getInterviewDate());
    }

    @Test
    void testSetInterviewDate_withNullIDs() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        OfferApplication offerApplication = getDummyOfferApp();
        when(offerApplicationService.setInterviewDate(any(), any()))
                .thenThrow(new IllegalArgumentException("L'identifiant de l'offre ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/setdate/" + offerApplication.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'offre ne peut pas être vide");
    }

    @Test
    void testSetInterviewDate_withOfferAppIdNotExist() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        when(offerApplicationService.setInterviewDate(any(), any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas d'offre associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/setdate/" + 3L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'offre associé à cet identifiant");
    }

    @Test
    void testSetInterviewDate_withDateInvalid() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        when(offerApplicationService.setInterviewDate(any(), any()))
                .thenThrow(new DateNotValidException("La date choisie est invalide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/setdate/" + 3L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La date choisie est invalide");
    }

    @Test
    void testGetAllByOfferStatusAndStudentID_withValidEntries() throws Exception {
        List<OfferApplication> offerApplicationList = getDummyOfferAppList();
        when(offerApplicationService.getAllByOfferStatusAndStudentID(any(), any()))
                .thenReturn(offerApplicationList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/cv_sent/" + 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final List<OfferApplication> actualOfferApplicationList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(actualOfferApplicationList.size()).isEqualTo(offerApplicationList.size());
    }

    @Test
    void testGetAllByOfferStatusAndStudentID_withNullEntries() throws Exception {
        when(offerApplicationService.getAllByOfferStatusAndStudentID(any(), any()))
                .thenThrow(new IllegalArgumentException("L'identifiant de l'étudiant ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/cv_sent/" + 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'étudiant ne peut pas être vide");
    }

    @Test
    public void testGetAllOffersApp() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        when(offerApplicationService.getAllOffersApplication()).thenReturn(offerApplicationsList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferApplication> actualList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualList.size()).isEqualTo(offerApplicationsList.size());
    }

    @Test
    public void testUpdateStatusIsAccepted() throws Exception {
        OfferApplication offerApplication = getDummyOfferApp();
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO(offerApplication.getId(), Status.STAGE_TROUVE);
        when(offerApplicationService.updateStatus(any())).thenReturn("Statut changé, attendez la signature du contrat");

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/student/update_status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(updateStatusDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Statut changé, attendez la signature du contrat");

    }

    @Test
    public void testUpdateStatusIsAccepted_withIdNull() throws Exception {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO(null, Status.STAGE_TROUVE);
        when(offerApplicationService.updateStatus(any()))
                .thenThrow(new IllegalArgumentException("L'identifiant de l'offre ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/student/update_status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(updateStatusDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'offre ne peut pas être vide");
    }

    @Test
    public void testGetAllOffersByStudentsApplied() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        Student dummyStudent = getDummyStudent();
        when(offerApplicationService.getAllOffersStudentApplied(any())).thenReturn(offerApplicationsList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/offerApp/student/" + dummyStudent.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferApplication> actualList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualList.size()).isEqualTo(offerApplicationsList.size());
    }

    @Test
    public void testGetAllOffersByStudentApplied_withIdNull() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(offerApplicationService.getAllOffersStudentApplied(any()))
                .thenThrow(new IllegalArgumentException("L'identifiant de l'étudiant ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/offerApp/student/" + dummyStudent.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'étudiant ne peut pas être vide");
    }

    @Test
    public void testGetAllOffersByStudentApplied_withInvalidId() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(offerApplicationService.getAllOffersStudentApplied(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/offerApp/student/" + dummyStudent.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'étudiant associé à cet identifiant");
    }

    @Test
    public void testGetOffersApplicationStageTrouverIdManager() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        Manager dummyManager = getDummyManager();
        when(offerApplicationService.getOffersApplicationsStageTrouverManagerId(any())).thenReturn(offerApplicationsList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/manager/" + dummyManager.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferApplication> actualList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualList.size()).isEqualTo(offerApplicationsList.size());
    }

    @Test
    public void testGetOffersApplicationStageTrouver_withIdNull() throws Exception {
        Manager dummyManager = getDummyManager();
        when(offerApplicationService.getOffersApplicationsStageTrouverManagerId(any()))
                .thenThrow(new IllegalArgumentException("L'identifiant du gestionnaire ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/manager/" + dummyManager.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant du gestionnaire ne peut pas être vide");
    }

    @Test
    public void testGetOffersApplicationStageTrouver_withInvalidId() throws Exception {
        Manager dummyManager = getDummyManager();
        when(offerApplicationService.getOffersApplicationsStageTrouverManagerId(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas de gestionnaire associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/manager/" + dummyManager.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de gestionnaire associé à cet identifiant");
    }

    private OfferAppDTO getDummyOfferAppDto() {
        OfferAppDTO offerAppDTO = new OfferAppDTO();
        offerAppDTO.setIdStudent(1L);
        offerAppDTO.setIdOffer(1L);

        return offerAppDTO;
    }

    private OfferApplication getDummyOfferApp() {
        OfferApplication dummyOfferApplicationDTO = new OfferApplication();
        dummyOfferApplicationDTO.setOffer(getDummyOffer());
        dummyOfferApplicationDTO.setCurriculum(new Curriculum());
        dummyOfferApplicationDTO.setId(1L);

        return dummyOfferApplicationDTO;
    }

    private Manager getDummyManager() {
        Manager dummyManager = new Manager();
        dummyManager.setId(1L);
        dummyManager.setLastName("Candle");
        dummyManager.setFirstName("Tea");
        dummyManager.setEmail("admin@admin.com");
        dummyManager.setPassword("admin");
        return dummyManager;
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        return dummyOffer;
    }

    private Curriculum getDummyCurriculum() {
        Curriculum dummyCurriculum = new Curriculum();

        dummyCurriculum.setId(1L);
        dummyCurriculum.setData("some xml".getBytes());
        dummyCurriculum.setName("sample.pdf");
        dummyCurriculum.setStudent(new Student());
        return dummyCurriculum;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Candle");
        dummyStudent.setFirstName("Tea");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }

    private List<Student> getDummyStudentList() {
        List<Student> dummyStudentList = new ArrayList<>();
        dummyStudentList.add(getDummyStudent());
        dummyStudentList.add(getDummyStudent());
        dummyStudentList.add(getDummyStudent());
        return dummyStudentList;

    }


    private List<OfferApplication> getDummyOfferAppList() {
        List<OfferApplication> offerApplicationList = new ArrayList<>();
        OfferApplication dummyOfferApplication = new OfferApplication();
        dummyOfferApplication.setOffer(getDummyOffer());
        dummyOfferApplication.setCurriculum(getDummyCurriculum());
        dummyOfferApplication.setId(1L);
        dummyOfferApplication.setStatus(Status.CV_ENVOYE);
        offerApplicationList.add(dummyOfferApplication);

        dummyOfferApplication.setId(2L);
        offerApplicationList.add(dummyOfferApplication);

        dummyOfferApplication.setId(3L);
        offerApplicationList.add(dummyOfferApplication);

        return offerApplicationList;
    }

}