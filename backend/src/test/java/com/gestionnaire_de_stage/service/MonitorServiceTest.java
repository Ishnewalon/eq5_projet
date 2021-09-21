package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonitorServiceTest {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitorService monitorService;

    @BeforeAll
    public void insertData() {
        Monitor monitor1 = new Monitor();
        monitor1.setId(1L);
        monitor1.setFirstName("Steph");
        monitor1.setName("Kazuk");
        monitor1.setEmail("stepotato@gmail.com");
        monitor1.setNumTel("5145555112");
        monitor1.setDepartment("Informatique");
        monitor1.setPassword("testPassword");

        Monitor monitor2 = new Monitor();
        monitor2.setId(2L);
        monitor2.setFirstName("Ouss");
        monitor2.setName("ama");
        monitor2.setEmail("ouste@gmail.com");
        monitor2.setNumTel("5145555112");
        monitor2.setDepartment("Informatique");
        monitor2.setPassword("testPassword");

        Monitor monitor3 = new Monitor();
        monitor3.setId(3L);
        monitor3.setFirstName("same");
        monitor3.setName("dude");
        monitor3.setEmail("dudesame@gmail.com");
        monitor3.setNumTel("5145555112");
        monitor3.setDepartment("Informatique");
        monitor3.setPassword("testPassword");

        monitorRepository.saveAll(Arrays.asList(monitor1, monitor2, monitor3));
    }

    @Test
    public void testFindAll() {
        int actual = monitorRepository.findAll().size();

        assertEquals(3, actual);
    }

    @Test
    public void testCreate_withValidMonitor() {
        Monitor monitor = new Monitor();
        monitor.setName("toto");
        monitor.setFirstName("titi");
        monitor.setEmail("toto@gmail.com");
        monitor.setPassword("testPassword");

        Optional<Monitor> actual = monitorService.create(monitor);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testCreate_withNullMonitor() {
        Optional<Monitor> actual = monitorService.create(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetByID_withValidID() {
        Long validID = 1L;

        Optional<Monitor> actual = monitorService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetByID_withNullID() {
        Optional<Monitor> actual = monitorService.getOneByID(null);

        assertTrue(actual.isEmpty ());
    }

    @Test
    public void testGetAll() {
        int expectedLength = 3;

        List<Monitor> monitorList = monitorService.getAll();

        assertEquals(expectedLength, monitorList.size());
    }

    @Test
    public void testUpdate_withValidEntries() {
        Monitor monitor = new Monitor();
        monitor.setName("toto");
        monitor.setFirstName("titi");
        monitor.setEmail("toto@gmail.com");
        monitor.setPassword("testPassword");
        Long validID = 2L;

        Optional<Monitor> actual = monitorService.update(monitor, validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testUpdate_withNullEntries() {
        Monitor monitor = new Monitor();
        monitor.setName("toto");
        monitor.setFirstName("titi");
        monitor.setEmail("toto@gmail.com");
        monitor.setPassword("testPassword");

        Optional<Monitor> actual = monitorService.update(monitor, null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testDelete_withValidID() {
        Long validID = 1L;

        boolean actual = monitorService.deleteByID(validID);

        assertTrue(actual);
    }

    @Test
    public void testDelete_withNullID() {
        boolean actual = monitorService.deleteByID(null);

        assertFalse(actual);
    }

    @Test
    public void testfindStudentByEmailAndPassword() {
        String email = "stepotato@gmail.com";
        String password = "testPassword";

        Monitor monitor = monitorRepository.findMonitorByEmailAndPassword(email, password);
        String actual = monitor.getFirstName();

        assertEquals(actual, "Steph");
    }

    @Test
    public void testExistsByEmailAndPassword_withValidEntries() {
        String email = "stepotato@gmail.com";
        String password = "testPassword";

        boolean actual = monitorRepository.existsByEmailAndPassword(email, password);

        assertTrue(actual);
    }

    @Test
    public void testExistsByEmailAndPassword_withNullEntries() {
        boolean actual = monitorRepository.existsByEmailAndPassword(null, null);

        assertFalse(actual);
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() {
        String email = "stepotato@gmail.com";
        String password = "testPassword";

        Optional<Monitor> actual = monitorService.getOneByEmailAndPassword(email, password);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEntries() {
        Optional<Monitor> actual = monitorService.getOneByEmailAndPassword(null, null);

        assertTrue(actual.isEmpty());
    }
}
