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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class OfferControllerTestBroken {

    @Autowired
    private OfferRepository offerRepository;

    private MockMvc mockMvc;

    @Autowired
    private OfferService offerService;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private WebApplicationContext context;

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

    private List<Monitor> createMonitorDummies() {
        Monitor mon1 = getDummyMonitor();
        mon1.setId(1L);

        Monitor mon2 = getDummyMonitor();
        mon2.setId(2L);

        Monitor mon3 = getDummyMonitor();
        mon3.setId(3L);

        return monitorRepository.saveAll(List.of(mon1, mon2, mon3));
    }

    @BeforeAll
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MockitoAnnotations.openMocks(this);

        List<Monitor> monitors = createMonitorDummies();
        List<Manager> managers = createManagerDummies();

        Offer offer1 = getDummyOffer(monitors.get(0));
        offer1.setId(1L);

        Offer offer2 = getDummyOffer(monitors.get(1));
        offer2.setId(2L);

        Offer offer3 = getDummyOffer(monitors.get(2));
        offer3.setId(3L);

        Offer offer4 = getDummyOffer(managers.get(0));
        offer4.setId(4L);

        Offer offer5 = getDummyOffer(managers.get(1));
        offer5.setId(5L);

        Offer offer6 = getDummyOffer(managers.get(2));
        offer6.setId(6L);

        offerRepository.saveAll(List.of(offer1, offer2, offer3, offer4, offer5, offer6));
    }

    private List<Manager> createManagerDummies() {
        Manager man1 = getDummyManager();
        man1.setId(1L);

        Manager man2 = getDummyManager();
        man1.setId(2L);

        Manager man3 = getDummyManager();
        man3.setId(3L);

        return managerRepository.saveAll(List.of(man1, man2, man3));
    }

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
        List<OfferDTO> myDummyOffers = new ArrayList<>();
        Manager manager = getDummyManager();
        manager.setId(1L);

        offerService.create(getDummyOffer(manager));

        for (int i = 0; i < 5; i++) {
            var dummyOffer = getDummyOffer(manager);
            dummyOffer.setTitle(dummyOffer.getTitle() + i);
            dummyOffer.setDepartment(department);
            offerService.create(dummyOffer).ifPresent(value -> myDummyOffers.add(offerService.mapToOfferDTO(value)));
        }

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var offers = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<OfferDTO>>() {
        });

        assertArrayEquals(myDummyOffers.toArray(), offers.toArray());
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetOffersByDepartmentWithNoOffer() throws Exception {
        String department = "myDepartmentWithNoOffer";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var offers = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<OfferDTO>>() {
        });

        assertArrayEquals(new ArrayList<>().toArray(), offers.toArray());
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetOffersByDepartmentWithNoDepartment() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        assertTrue(mvcResult.getResponse().getContentAsString().contains("Erreur: Le departement n'est pas precise"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }
}
