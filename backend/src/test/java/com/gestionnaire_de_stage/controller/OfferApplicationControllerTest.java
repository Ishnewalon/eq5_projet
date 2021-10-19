package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.OfferApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;


@WebMvcTest(OfferApplicationController.class)
class OfferApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferApplicationService offerApplicationService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testStudentApplyToOffer() throws Exception {
        when(offerApplicationService.create(any(), any())).thenReturn(Optional.of(getDummyOfferApp()));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(CREATED.value());
        assertThat(response.getContentAsString()).contains("Succes: candidature envoyer!");
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
        assertThat(response.getContentAsString()).contains("Erreur: candidature deja envoye!");
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
        assertThat(response.getContentAsString()).contains("Erreur: Offre ou Curriculum non existant!");
    }

    @Test
    public void testStudentApplyToOffer_withCurriculumNonExistant() throws Exception {
        when(offerApplicationService.create(any(), any())).thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(getDummyOfferAppDto())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Offre ou Curriculum non existant!");
    }

    @Test
    public void testStudentApplyToOffer_withDTOWithNoOfferId() throws Exception {
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdOffer(null);
        when(offerApplicationService.create(any(), any())).thenThrow(new IllegalArgumentException("Erreur: Le id de l'offre ne peut pas etre null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferAppDto)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le id de l'offre ne peut pas etre null");
    }

    @Test
    public void testStudentApplyToOffer_withDTOWithNoCurriculumId() throws Exception {
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdCurriculum(null);
        when(offerApplicationService.create(any(), any())).thenThrow(new IllegalArgumentException("Erreur: Le id du curriculum ne peut pas etre null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/applications/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferAppDto)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le id du curriculum ne peut pas etre null");
    }

    private OfferAppDTO getDummyOfferAppDto() {
        OfferAppDTO offerAppDTO = new OfferAppDTO();
        offerAppDTO.setIdOffer(1L);
        offerAppDTO.setIdCurriculum(1L);

        return offerAppDTO;
    }

    private OfferApplication getDummyOfferApp() {
        OfferApplication dummyOfferApplicationDTO = new OfferApplication();
        dummyOfferApplicationDTO.setOffer(getDummyOffer());
        dummyOfferApplicationDTO.setCurriculum(getDummyCurriculum());
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

    private Curriculum getDummyCurriculum() {
        Curriculum dummyCurriculum = new Curriculum();

        dummyCurriculum.setId(1L);
        dummyCurriculum.setData("some xml".getBytes());
        dummyCurriculum.setName("fileeeename");
        dummyCurriculum.setStudent(new Student());
        return dummyCurriculum;
    }
}