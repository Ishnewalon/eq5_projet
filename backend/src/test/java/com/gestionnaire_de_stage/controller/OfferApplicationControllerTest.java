package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestionnaire_de_stage.dto.CurriculumDTO;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.exception.StudentHasNoCurriculumException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.service.CurriculumService;
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferApplicationService offerApplicationService;

    @MockBean
    private CurriculumService curriculumService;

    private ObjectMapper MAPPER = new ObjectMapper();

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
        when(offerApplicationService.create(any(), any())).thenThrow(new StudentAlreadyAppliedToOfferException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Candidature déjà envoyé!");
    }

    @Test
    public void testStudentApplyToOffer_withOfferNonExistant() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre ou étudiant non existant!");
    }

    @Test
    public void testStudentApplyToOffer_withStudentNonExistant() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre ou étudiant non existant!");
    }

    @Test
    public void testStudentApplyToOffer_withDTOWithNoOfferId() throws Exception {
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdOffer(null);
        when(offerApplicationService.create(any(), any())).thenThrow(new IllegalArgumentException("L'id de l'offre ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferAppDto)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'id de l'offre ne peut pas être null");
    }

    @Test
    public void testStudentApplyToOffer_withDTOWithNoStudentId() throws Exception {
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdStudent(null);
        when(offerApplicationService.create(any(), any())).thenThrow(new IllegalArgumentException("Le id du curriculum ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferAppDto)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du curriculum ne peut pas être null");
    }

    @Test
    public void testStudentApplyToOffer_withCvInvalid() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new StudentHasNoCurriculumException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Vous devez d'abord ajouter un curriculum!");
    }

    @Test
    public void testGetOffersApplicationStageTrouver() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        Manager dummyManager = getDummyManager();
        when(offerApplicationService.getOffersApplicationsStageTrouver(any())).thenReturn(offerApplicationsList);

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
        when(offerApplicationService.getOffersApplicationsStageTrouver(any()))
                .thenThrow(new IllegalArgumentException("L'id du gestionnaire ne peut pas être null!"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/manager/" + dummyManager.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'id du gestionnaire ne peut pas être null!");
    }

    @Test
    public void testGetOffersApplicationStageTrouver_withInvalidId() throws Exception {
        Manager dummyManager = getDummyManager();
        when(offerApplicationService.getOffersApplicationsStageTrouver(any()))
                .thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/manager/" + dummyManager.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le gestionnaire n'existe pas!");
    }

    @Test
    public void testViewStudentsAppliedOffer_withValidEntries() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        List<CurriculumDTO> curriculumDTOList = getDummyCurriculumDTOList();
        String email = "rolling@email.com";
        when(offerApplicationService.getAllByOfferCreatorEmail(any()))
                .thenReturn(offerApplicationsList);
        when(curriculumService.mapToCurriculumDTOList(any()))
                .thenReturn(curriculumDTOList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/{}", email)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<CurriculumDTO> actualCurriculumDTOs = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualCurriculumDTOs.size()).isEqualTo(3);
    }

    @Test
    public void testViewStudentsAppliedOffer_withNullEmail() throws Exception {
        String email = "rolling@email.com";
        when(offerApplicationService.getAllByOfferCreatorEmail(any()))
                .thenThrow(new IllegalArgumentException("Le courriel ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/applications/applicants/{}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel ne peut pas être null");
    }

    @Test
    public void testViewStudentsAppliedOffer_withEmptyList() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        String email = "rolling@email.com";
        when(offerApplicationService.getAllByOfferCreatorEmail(any()))
                .thenReturn(offerApplicationsList);
        when(curriculumService.mapToCurriculumDTOList(any()))
                .thenThrow(new IllegalArgumentException("La liste d'offre ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/applications/applicants/{}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La liste d'offre ne peut pas être vide");
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
                .thenThrow(new IllegalArgumentException("L'id de l'offre ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/applications/setdate/" + offerApplication.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'id de l'offre ne peut pas être null");
    }

    @Test
    void testSetInterviewDate_withOfferAppIdNotExist() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        when(offerApplicationService.setInterviewDate(any(), any()))
                .thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/applications/setdate/" + 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Impossible de trouver l'offre avec cette ID!");
    }

    @Test
    void testSetInterviewDate_withDateInvalid() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        when(offerApplicationService.setInterviewDate(any(), any()))
                .thenThrow(DateNotValidException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/applications/setdate/" + 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(LocalDateTime.now())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La date entrée est invalide!");
    }

    @Test
    void testGetAllByOfferStatusAndStudentID_withValidEntries() throws Exception {
        List<OfferApplication> offerApplicationList = getDummyOfferAppList();
        when(offerApplicationService.getAllByOfferStatusAndStudentID(any(), any()))
                .thenReturn(offerApplicationList);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/applications/all_applied_on/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final List<OfferApplication> actualOfferApplicationList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(actualOfferApplicationList.size()).isEqualTo(offerApplicationList.size());
    }

    @Test
    void testGetAllByOfferStatusAndStudentID_withNullEntries() throws Exception {
        when(offerApplicationService.getAllByOfferStatusAndStudentID(any(), any()))
                .thenThrow(new IllegalArgumentException("L'id du student ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/applications/all_applied_on/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'id du student ne peut pas être null");
    }

    @Test
    public void testGetAllOffersByStudentsApplied() throws Exception {
        List<OfferApplication> offerApplicationsList = getDummyOfferAppList();
        Student dummyStudent = getDummyStudent();
        when(offerApplicationService.getAllOffersStudentApplied(any())).thenReturn(offerApplicationsList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/student/" + dummyStudent.getId())
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
                .thenThrow(new IllegalArgumentException("L'id du student ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/student/" + dummyStudent.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'id du student ne peut pas être null");
    }

    @Test
    public void testGetAllOffersByStudentApplied_withInvalidId() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(offerApplicationService.getAllOffersStudentApplied(any()))
                .thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/applications/applicants/student/" + dummyStudent.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'étudiant n'existe pas");
    }

    @Test
    public void testUpdateStatusIsAccepted() throws Exception {
        OfferApplication offerApplication = getDummyOfferApp();
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO(offerApplication.getId(), true);
        when(offerApplicationService.updateStatus(any())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/student/update_status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(updateStatusDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Status changé, attendez la signature du contrat");

    }

    @Test
    public void testUpdateStatusIsAccepted_withIdNull() throws Exception {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO(null, true);
        when(offerApplicationService.updateStatus(any()))
                .thenThrow(new IllegalArgumentException("Le id ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/student/update_status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(updateStatusDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id ne peut pas être null");
    }

    @Test
    public void testUpdateStatusIsAccepted_withIdInvalid() throws Exception {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO(45433L, true);
        when(offerApplicationService.updateStatus(any()))
                .thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/student/update_status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(updateStatusDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre non existante!");
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

    private Offer getDummyOfferWithCreator() {
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

    private Manager getDummyManager() {
        Manager dummyManager = new Manager();
        dummyManager.setId(1L);
        dummyManager.setLastName("Candle");
        dummyManager.setFirstName("Tea");
        dummyManager.setEmail("admin@admin.com");
        dummyManager.setPassword("admin");
        return dummyManager;
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

    private List<CurriculumDTO> getDummyCurriculumDTOList() {
        List<CurriculumDTO> curriculumDTOList = new ArrayList<>();
        CurriculumDTO curriculumDTO1 = new CurriculumDTO();
        curriculumDTO1.setFirstName("Adam");
        curriculumDTO1.setLastName("Mold");
        curriculumDTO1.setFileName("AM_CV");
        curriculumDTO1.setFile(new byte[32 * 1024]);
        curriculumDTOList.add(curriculumDTO1);

        CurriculumDTO curriculumDTO2 = new CurriculumDTO();
        curriculumDTO2.setFirstName("Summer");
        curriculumDTO2.setLastName("Winter");
        curriculumDTO2.setFileName("SW_CV");
        curriculumDTO2.setFile(new byte[65 * 1024]);
        curriculumDTOList.add(curriculumDTO2);

        CurriculumDTO curriculumDTO3 = new CurriculumDTO();
        curriculumDTO3.setFirstName("John");
        curriculumDTO3.setLastName("Belushi");
        curriculumDTO3.setFileName("JB_CV");
        curriculumDTO3.setFile(new byte[53 * 1024]);
        curriculumDTOList.add(curriculumDTO3);

        return curriculumDTOList;
    }
}