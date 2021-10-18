package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private ManagerService managerService;

    private Offer offer;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testMonitorOfferCreate_withValidEntry() throws Exception {
        OfferDTO offerDTO = getDummyOfferDTO();

        offer = getDummyOffer();
        offer.setId(null);

        when(offerService.mapToOffer(offerDTO)).thenReturn(offer);
        when(offerService.create(any())).thenReturn(getDummyOffer());

        MvcResult mvcResult = mockMvc.perform(
                post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(offerDTO))
        ).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        Offer returnedOffer = MAPPER.readValue(responseString, Offer.class);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertThat(returnedOffer.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void testMonitorOfferCreate_withNullEntry() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(MAPPER.writeValueAsString(null))
        ).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        String responseString = mvcResult.getResponse().getContentAsString();
        assertThat(responseString).contains("Required");
    }

    @Test
    public void testMonitorOfferCreate_withInvalidId() throws Exception {
        OfferDTO offerDto = getDummyOfferDTO();
        offerDto.setCreator_id(45L);

        when(monitorService.getOneByID(45L)).thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
            post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(MAPPER.writeValueAsString(offerDto))
        ).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        final String responseString = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertThat(responseString).containsIgnoringCase("le moniteur n'existe pas");
    }

    @Test
    public void testMonitorOfferCreate_withAlreadyExistingOffer() throws Exception{
        OfferDTO offerDto = getDummyOfferDTO();
        offer = getDummyOffer();
        offer.setId(null);

        when(offerService.mapToOffer(offerDto)).thenReturn(offer);
        when(offerService.create(offer)).thenThrow(new OfferAlreadyExistsException());

        MvcResult mvcResult = mockMvc.perform(
                post("/offers/monitor/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(offerDto))
        ).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre existe déjà");
    }

    @Test
    public void testUpdateOffer_withNullId() throws Exception{
        offer = getDummyOffer();
        offer.setId(null);
        when(offerService.update(offer)).thenThrow(new IllegalArgumentException("L'id est null"));

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(offer))).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        assertThat(responseString).isEqualTo("L'id est null");
    }

    @Test
    public void testUpdateOffer_withEmptyOffer() throws Exception{
        offer = new Offer();
        when(offerService.update(offer)).thenThrow(new IllegalArgumentException("L'id est null"));

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(offer))).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        assertThat(responseString).isEqualTo("L'id est null");
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws Exception {
        offer = getDummyOffer();
        when(offerService.update(any())).thenReturn(offer);

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(offer))).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        Offer returnedOffer = MAPPER.readValue(responseString, Offer.class);

        assertThat(returnedOffer).isEqualTo(offer);
    }

    @Test
    public void testGetOffers_withValidOffers() throws Exception {
        List<Offer> list = getDummyArrayOffer();
        when(offerService.getAll()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        List<Offer> returnedOffers = MAPPER.readValue(responseString, new TypeReference<>() {});

        assertThat(returnedOffers).isEqualTo(list);
    }


    @Test
    public void testGetOffersByDepartment() throws Exception {
        String department = "myDepartment";
        when(offerRepository.findAllByDepartment(any())).thenReturn(getDummyArrayOffer());

        MvcResult mvcResult = mockMvc.perform(get(String.format("/offers/%s", department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<OfferDTO> returnedOfferDtos = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(returnedOfferDtos).isEqualTo(offerService.mapArrayToOfferDTO(getDummyArrayOffer()));
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() throws Exception {
        String department = "myDepartmentWithNoOffer";

        MvcResult mvcResult = mockMvc.perform(get(String.format("/offers/%s", department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<OfferDTO> offers = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertThat(offers).isEmpty();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoDepartment() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/offers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getContentAsString()).contains("Le departement n'est pas precise");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private List<Offer> getDummyArrayOffer() {
        List<Offer> myList = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            Offer dummyOffer = getDummyOffer();
            dummyOffer.setId(i);
            myList.add(dummyOffer);
        }
        return myList;
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
        offerDTO.setCreator_id(1L);
        offerDTO.setSalary(18.0d);
        offerDTO.setDescription("Une description");
        offerDTO.setAddress("Addresse du cégep");
        offerDTO.setTitle("Offer title");
        offerDTO.setDepartment("Department name");
        return offerDTO;
    }
}
