package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.User;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    private Monitor getDummyMonitor() {
        Monitor monitor = new Monitor();
        monitor.setFirstName("Ouss");
        monitor.setLastName("ama");
        monitor.setEmail("ouste@gmail.com");
        monitor.setPhone("5145555112");
        monitor.setDepartment("Informatique");
        monitor.setPassword("testPassword");
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

    @BeforeAll
    public void before(){
        MockitoAnnotations.openMocks(this);
        Offer offer1 = getDummyOffer(getDummyMonitor());
        offer1.setId(1L);

        Offer offer2 = getDummyOffer(getDummyMonitor());
        offer2.setId(2L);

        Offer offer3 = getDummyOffer(getDummyMonitor());
        offer3.setId(3L);

        List<Offer> offers =offerRepository.saveAll(List.of(offer1, offer2, offer3));
        offers.forEach(System.out::println);
    }

    @Test
    @Order(1)
    @DisplayName("Injection tests")
    public void testMockitoInjections() {
        assertNotNull(offerService);
        assertNotNull(offerRepository);
    }

    @Test
    public void testFindAll() {
        int actual = offerService.getAll().size();

        assertEquals(3, actual);
    }

    @Test
    public void testCreate_withValidOffer() {
        Offer offer = getDummyOffer(getDummyMonitor());

        Optional<Offer> actual = offerService.create(offer);
        assertTrue(actual.isPresent());
    }

    @Test
    public void testCreate_withNullOffer() {
        Optional<Offer> actual = offerService.create(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetByID_withValidID() {
        Long validID = 1L;

        Optional<Offer> actual = offerService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetByID_withNullID() {
        Optional<Offer> actual = offerService.getOneByID(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetAll() {
        int expectedLength = 3;

        List<Offer> studentList = offerService.getAll();

        assertEquals(expectedLength, studentList.size());
    }

    @Test
    public void testUpdate_withValidEntries(){
        Offer offer = getDummyOffer(getDummyMonitor());

        Long validID = 2L;

        Optional<Offer> actual = offerService.update(offer, validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testUpdate_withNullEntries() {
        Offer offer = getDummyOffer(getDummyMonitor());
        Optional<Offer> actual = offerService.update(offer, null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testDelete_withValidID() {
        Long validId = 1L;

        boolean actual = offerService.deleteByID(validId);

        assertTrue(actual);
    }

    @Test
    public void testDelete_withNullID() {
        boolean actual = offerService.deleteByID(null);

        assertFalse(actual);
    }
}