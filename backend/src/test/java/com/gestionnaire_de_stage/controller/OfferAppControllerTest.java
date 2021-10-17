package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.OfferAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;


@WebMvcTest(OfferAppController.class)
class OfferAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferAppService offerAppService;

    @Test
    public void testStudentApplyToOffer() throws Exception {
        // Arrange
        when(offerAppService.create(any(), any())).thenReturn(Optional.of(getDummyOfferApp()));
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(CREATED.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Succes: candidature envoyer!");
    }

    @Test
    public void testStudentApplyToOfferAgain() throws Exception {
        // Arrange
        when(offerAppService.create(any(), any())).thenThrow(new StudentAlreadyAppliedToOfferException());
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: candidature deja envoye!");
    }

    @Test
    public void testStudentApplyToOfferWithOfferNonExistant() throws Exception {
        // Arrange
        when(offerAppService.create(any(), any())).thenThrow(new IdDoesNotExistException());
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Offre ou Curriculum non existant!");
    }

    @Test
    public void testStudentApplyToOfferWithNoCurriculum() throws Exception {
        // Arrange
        when(offerAppService.create(any(), any())).thenThrow(new IdDoesNotExistException());
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Offre ou Curriculum non existant!");
    }

    @Test
    public void testStudentApplyToOfferWithDTOWithNoOfferId() throws Exception {
        // Arrange
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdOffer(null);
        when(offerAppService.create(any(), any())).thenThrow(new IllegalArgumentException("Erreur: Le id de l'offre ne peut pas etre null"));
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dummyOfferAppDto)))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le id de l'offre ne peut pas etre null");
    }

    @Test
    public void testStudentApplyToOfferWithDTOWithNoCurriculumId() throws Exception {
        // Arrange
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdCurriculum(null);
        when(offerAppService.create(any(), any())).thenThrow(new IllegalArgumentException("Erreur: Le id du curriculum ne peut pas etre null"));
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dummyOfferAppDto)))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le id du curriculum ne peut pas etre null");
    }

    private OfferAppDTO getDummyOfferAppDto() {
        OfferAppDTO offerAppDTO = new OfferAppDTO();
        offerAppDTO.setIdOffer(1L);
        offerAppDTO.setIdCurriculum(1L);

        return offerAppDTO;
    }

    private OfferApp getDummyOfferApp() {
        OfferApp offerAppDTO = new OfferApp();
        offerAppDTO.setOffer(getDummyOffer());
        offerAppDTO.setCurriculum(getDummyCurriculum());
        offerAppDTO.setId(1L);

        return offerAppDTO;
    }

    private Offer getDummyOffer() {
        Offer offer = new Offer();
        offer.setDepartment("Un departement");
        offer.setAddress("ajsaodas");
        offer.setId(1L);
        offer.setDescription("oeinoiendw");
        offer.setSalary(10);
        offer.setTitle("oeinoiendw");
        return offer;
    }

    private Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();

        curriculum.setId(1L);
        curriculum.setData("some xml".getBytes());
        curriculum.setName("fileeeename");
        curriculum.setStudent(new Student());
        return curriculum;
    }
}