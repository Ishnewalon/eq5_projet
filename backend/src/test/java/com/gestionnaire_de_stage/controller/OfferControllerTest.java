package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.repository.OfferRepository;
import com.gestionnaire_de_stage.service.ManagerService;
import com.gestionnaire_de_stage.service.MonitorService;
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

    @MockBean
    private OfferRepository offerRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private MonitorService monitorService;


    private Offer dummyOffer;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testOfferCreate_withValidEntry() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.mapToOffer(dummyOfferDTO)).thenReturn(dummyOffer);
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
        when(offerService.mapToOffer(any())).thenReturn(new Offer());
        when(offerService.create(any())).thenThrow(new IllegalArgumentException("Offre est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new OfferDTO())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre est null");
    }

    @Test
    public void testOfferCreate_withNullEmail() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOfferDTO.setCreator_email("goaway@email.com");
        when(monitorService.getOneByEmail(dummyOfferDTO.getCreator_email()))
                .thenThrow(new IllegalArgumentException("Le courriel de l'utilisateur ne peut être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).containsIgnoringCase("Le courriel de l'utilisateur ne peut être null");
    }

    @Test
    public void testOfferCreate_withAlreadyExistingOffer() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.mapToOffer(dummyOfferDTO)).thenReturn(dummyOffer);
        when(offerService.create(dummyOffer)).thenThrow(new OfferAlreadyExistsException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre existe déjà");
    }

    @Test
    public void testOfferCreate_withInvalidEmail() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.mapToOffer(dummyOfferDTO)).thenReturn(dummyOffer);
        when(monitorService.getOneByEmail(any())).thenThrow(new EmailDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/offers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel n'existe pas");
    }

    @Test
    public void testUpdateOffer_withNullId() throws Exception {
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.update(dummyOffer)).thenThrow(new IllegalArgumentException("L'id est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOffer)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("L'id est null");
    }

    @Test
    public void testUpdateOffer_withEmptyOffer() throws Exception {
        dummyOffer = new Offer();
        when(offerService.update(dummyOffer)).thenThrow(new IllegalArgumentException("L'id est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOffer)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("L'id est null");
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws Exception {
        dummyOffer = getDummyOffer();
        when(offerService.update(any())).thenReturn(dummyOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOffer)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Offer returnedOffer = MAPPER.readValue(response.getContentAsString(), Offer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffer).isEqualTo(dummyOffer);
    }

    @Test
    public void testUpdateOffer_withInvalidId() throws Exception {
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.update(dummyOffer)).thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOffer)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre non existante!");
    }

    @Test
    public void testGetOffers_withValidOffers() throws Exception {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getAll()).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
    }

    @Test
    public void testGetOffersByDepartment() throws Exception {
        String department = "myDepartment";
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        List<OfferDTO> mappedDTOS = offerService.mapArrayToOfferDTO(dummyArrayOffer);
        when(offerRepository.findAllByDepartment(any())).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferDTO> returnedOfferDtos = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(returnedOfferDtos).isEqualTo(mappedDTOS);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() throws Exception {
        String department = "myDepartmentWithNoOffer";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<OfferDTO> returnedOfferDtos = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(returnedOfferDtos).isEmpty();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoDepartment() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentAsString()).contains("Le département n'est pas précisé");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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
