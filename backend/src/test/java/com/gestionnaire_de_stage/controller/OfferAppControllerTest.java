package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferAppDTO;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import com.gestionnaire_de_stage.service.OfferAppService;
import com.gestionnaire_de_stage.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(OfferAppController.class)
class OfferAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferAppService offerAppService;
    @MockBean
    private OfferService offerService;
    @MockBean
    private CurriculumService curriculumService;

    @Test
    public void testStudentApplyToOffer() throws Exception {
        // Arrange
        when(offerService.findOfferById(any())).thenReturn(Optional.of(new Offer()));
        when(curriculumService.getCurriculum(any())).thenReturn(new Curriculum());
        when(offerAppService.create(any(), any())).thenReturn(Optional.of(getDummyOfferApp()));
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Succes: candidature envoyer!");
    }

    @Test
    public void testStudentApplyToOfferAgain() throws Exception {
        // Arrange
        when(offerService.findOfferById(any())).thenReturn(Optional.of(new Offer()));
        when(curriculumService.getCurriculum(any())).thenReturn(new Curriculum());
        when(offerAppService.create(any(), any())).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.ALREADY_REPORTED.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: candidature deja envoye!");
    }
    @Test
    public void testStudentApplyToOfferWithOfferNonExistant() throws Exception {
        // Arrange
        when(offerService.findOfferById(any())).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Offre non existant!");
    }

    @Test
    public void testStudentApplyToOfferWithNoCurriculum() throws Exception {
        // Arrange
        when(offerService.findOfferById(any())).thenReturn(Optional.of(new Offer()));
        when(curriculumService.getCurriculum(any())).thenReturn(null);
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDummyOfferAppDto())))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: curriculum inexistant");
    }
    @Test
    public void testStudentApplyToOfferWithDTOWithNoOfferId() throws Exception {
        // Arrange
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdOffer(null);
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dummyOfferAppDto)))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le id de l'offre ne peut pas etre null");
    }
    @Test
    public void testStudentApplyToOfferWithDTOWithNoCurriculumId() throws Exception {
        // Arrange
        OfferAppDTO dummyOfferAppDto = getDummyOfferAppDto();
        dummyOfferAppDto.setIdCurriculum(null);
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/applications/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dummyOfferAppDto)))
                .andReturn();
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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