package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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


    @Test
    public void testMonitorOfferCreate_withValidEntries() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertEquals(Boolean.TRUE.toString(), mvcResult.getResponse().getContentAsString());
        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testManagerOfferCreate_withValidEntries() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(4L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));

        MvcResult mvcResult = mockMvc.perform(post("/offers/manager/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertEquals(Boolean.TRUE.toString(), mvcResult.getResponse().getContentAsString());
        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    }

    private Offer offer;

    @Test
    public void testUpdateOffer_withNullId() throws Exception{
        offer = getDummyOffer();
        offer.setId(null);
        when(offerService.update(offer)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();
        var actualOfferInString = mvcResult.getResponse().getContentAsString();

        assertThat(actualOfferInString).isEqualTo("Erreur : offre non existante!");
    }

    @Test
    public void testUpdateOffer_withEmptyOffer() throws Exception{
        offer = new Offer();
        when(offerService.update(offer)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var actualOfferInString = mvcResult.getResponse().getContentAsString();
        assertThat(actualOfferInString).isEqualTo("Erreur : offre non existante!");
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws Exception {
        offer = getDummyOffer();
        when(offerService.update(any())).thenReturn(Optional.of(offer));

        MvcResult mvcResult = mockMvc.perform(put("/offers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var actualOfferInString = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actualOfferInString, Offer.class)).isEqualTo(offer);
    }

    @Test
    public void testGetOffers_withValidOffers() throws Exception {
        List<Offer> list = getDummyListOffer();
        when(offerService.getAll()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actualOffersInString = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actualOffersInString,
                new TypeReference<List<Offer>>(){})).isEqualTo(list);
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

    private List<Offer> getDummyListOffer(){
        List<Offer> list = new ArrayList<>();
        Offer offer = getDummyOffer();
        offer.setId(2L);
        list.add(getDummyOffer());
        list.add(offer);
        return list;
    }

    @Test
    public void testGetOffersByDepartment() throws Exception {
        String department = "myDepartment";
        when(offerRepository.findAllByDepartment(any())).thenReturn(getDummyArrayOffer());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var offers = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<OfferDTO>>() {
        });

        assertThat(offers).isEqualTo(offerService.mapArrayToOfferDTO(getDummyArrayOffer()));
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() throws Exception {
        String department = "myDepartmentWithNoOffer";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var offers = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<OfferDTO>>() {
        });

        assertThat(offers).isEqualTo(Collections.emptyList());
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoDepartment() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le departement n'est pas precise");
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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

    private Monitor getDummyMonitor() {
        Monitor monitor = new Monitor();
        monitor.setFirstName("Ouss");
        monitor.setLastName("ama");
        monitor.setAddress("Cégep");
        monitor.setEmail("ouste@gmail.com");
        monitor.setPhone("5145555112");
        monitor.setDepartment("Informatique");
        monitor.setPassword("testPassword");
        monitor.setPostalCode("H0H0H0");
        return monitor;
    }

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    private Offer getDummyOffer(User user) {
        Offer offer = new Offer();
        offer.setTitle("Job title");
        offer.setAddress("Job address");
        offer.setDepartment("Department sample");
        offer.setDescription("Job description");
        offer.setSalary(18.0d);
        offer.setCreator(user);
        return offer;
    }
}
