package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.exception.OfferAlreadyTreatedException;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.OfferService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    private final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferService offerService;
    private Offer dummyOffer;

    @Test
    public void testOfferCreate_withValidEntry() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenReturn(getDummyOffer());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Offer returnedOffer = MAPPER.readValue(response.getContentAsString(), Offer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(returnedOffer.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void testOfferCreate_withNullEntry() throws Exception {
        when(offerService.create(any())).thenThrow(new IllegalArgumentException("L'offre ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new OfferDTO())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'offre ne peut pas être vide");
    }

    @Test
    public void testOfferCreate_withNullEmail() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenThrow(new IllegalArgumentException("Le courriel ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).containsIgnoringCase("Le courriel ne peut pas être vide");
    }

    @Test
    public void testOfferCreate_withInvalidId() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOfferDTO.setIdSession(null);
        when(offerService.create(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas de session associée à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).containsIgnoringCase("Il n'y a pas de session associée à cet identifiant");
    }

    @Test
    public void testOfferCreate_withAlreadyExistingOffer() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenThrow(new OfferAlreadyExistsException("Cette offre a déjà été créée"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Cette offre a déjà été créée");
    }

    @Test
    public void testOfferCreate_withInvalidEmail() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenThrow(new DoesNotExistException("L'email n'existe pas"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'email n'existe pas");
    }

    @Test
    public void testUpdateOffer_withNullId() throws Exception {
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.validation(any())).thenThrow(new IllegalArgumentException("L'identifiant de l'offre ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'offre ne peut pas être vide");
    }

    @Test
    public void testUpdateOffer_withOfferAlreadyTreated() throws Exception {
        dummyOffer = new Offer();
        when(offerService.validation(any())).thenThrow(new OfferAlreadyTreatedException("Cet offre a déjà été traitée"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Cet offre a déjà été traitée");
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws Exception {
        dummyOffer = getDummyOffer();
        when(offerService.validation(any())).thenReturn(dummyOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Offer returnedOffer = MAPPER.readValue(response.getContentAsString(), Offer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffer).isEqualTo(dummyOffer);
    }

    @Test
    public void testUpdateOffer_withInvalidId() throws Exception {
        dummyOffer = getDummyOffer();
        when(offerService.validation(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'offre associée à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'offre associée à cet identifiant");
    }


    @Test
    public void testGetOffersNotYetApplied() throws Exception {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getOffersNotYetApplied(any())).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/{0}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(returnedOffers).containsAll(dummyArrayOffer);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersNotYetApplied_withInvalidStudent() throws Exception {
        when(offerService.getOffersNotYetApplied(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(String.format("/offers/%s", 123412L))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'étudiant associé à cet identifiant");
    }


    @Test
    void testGetValidOffers() throws Exception {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getValidOffers()).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/valid")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
    }

    @Test
    void testGetNotValidatedOffers() throws Exception {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getNotValidatedOffers()).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/not_validated")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
    }

    private List<Offer> getDummyArrayOffer() {
        List<Offer> dummyArrayOffer = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            Offer dummyOffer = getDummyOffer();
            dummyOffer.setId(i);
            dummyArrayOffer.add(dummyOffer);
        }
        return dummyArrayOffer;
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

    private OfferDTO getDummyOfferDTO() {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setCreator_email("donewith@email.com");
        offerDTO.setSalary(18.0d);
        offerDTO.setDescription("Une description");
        offerDTO.setAddress("Addresse du cégep");
        offerDTO.setTitle("Offer title");
        offerDTO.setDepartment("Department name");
        return offerDTO;
    }
}
