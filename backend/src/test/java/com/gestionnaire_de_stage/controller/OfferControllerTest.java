package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.User;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import com.gestionnaire_de_stage.repository.OfferRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {

    @MockBean
    private OfferRepository offerRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private MonitorRepository monitorRepository;

    @MockBean
    private ManagerRepository managerRepository;


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


    @Test
    public void testMonitorOfferCreate_withNullEntries() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(null))).andReturn();

        var responseString = mvcResult.getResponse().getContentAsString();

        assertTrue(responseString.contains("body is missing"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }


    @Test
    public void testManagerOfferCreate_withNullEntries() throws Exception {
        OfferDTO offer = null;

        MvcResult mvcResult = mockMvc.perform(post("/offers/manager/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        var responseString = mvcResult.getResponse().getContentAsString();

        assertTrue(responseString.contains("body is missing"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }


    @Test
    public void testManagerOfferCreate_withNonExistentId() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(14L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));

        MvcResult mvcResult = mockMvc.perform(post("/offers/manager/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertEquals("Erreur: gestionnaire non existant!", mvcResult.getResponse().getContentAsString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testMonitorrOfferCreate_withNonExistentId() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(14L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertEquals("Erreur: moniteur non existant!", mvcResult.getResponse().getContentAsString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateMonitorOffer_withNullDepartment() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));
        offer.setDepartment(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le departement est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateMonitorOffer_withNullTItle() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));
        offer.setTitle(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le titre est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateMonitorOffer_withNullAddress() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));
        offer.setAddress(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("L'addresse est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateMonitorOffer_withNullDescription() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));
        offer.setDescription(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("La description est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateMonitorOffer_withInvalidSalary() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));
        offer.setSalary(-10);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le salaire n'est pas positif."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }


    @Test
    public void testCreateMonitorOffer_withInvalidCreatorId() throws Exception {
        Monitor monitor = getDummyMonitor();
        monitor.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(monitor));
        offer.setCreator_id(-1);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le id de l'utilsateur n'est pas positif."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateManagerOffer_withNullDepartment() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));
        offer.setDepartment(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/manager/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le departement est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateManagerOffer_withNullTItle() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));
        offer.setTitle(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/manager/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le titre est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateManagerOffer_withNullAddress() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));
        offer.setAddress(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/manager/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("L'addresse est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateManagerOffer_withNullDescription() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));
        offer.setDescription(null);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("La description est vide."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateManagerOffer_withInvalidSalary() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));
        offer.setSalary(-10);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le salaire n'est pas positif."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateManagerOffer_withInvalidCreatorId() throws Exception {
        Manager manager = getDummyManager();
        manager.setId(1L);

        OfferDTO offer = offerService.mapToOfferDTO(getDummyOffer(manager));
        offer.setCreator_id(-1);

        MvcResult mvcResult = mockMvc.perform(post("/offers/monitor/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(offer))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Le id de l'utilsateur n'est pas positif."));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
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
    public void testGetOffersByDepartmentWithNoOffer() throws Exception {
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
    public void testGetOffersByDepartmentWithNoDepartment() throws Exception {

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
