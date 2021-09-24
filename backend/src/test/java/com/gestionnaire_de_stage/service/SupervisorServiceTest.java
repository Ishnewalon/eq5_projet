package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepository.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SupervisorServiceTest {

    @Autowired
    SupervisorRepository supervisorRepository;

    @Autowired
    SupervisorService supervisorService;

    @BeforeAll
    public void insertData() {
        Supervisor supervisor1 = new Supervisor();
        supervisor1.setId(1L);
        supervisor1.setLastName("Keys");
        supervisor1.setFirstName("Harold");
        supervisor1.setEmail("keyh@gmail.com");
        supervisor1.setPassword("galaxy29");
        supervisor1.setDepartment("Comptabilité");
        supervisor1.setMatricule("04736");

        Supervisor supervisor2 = new Supervisor();
        supervisor2.setId(2L);
        supervisor2.setLastName("Hole");
        supervisor2.setFirstName("Van");
        supervisor2.setEmail("holv@gmail.com");
        supervisor2.setPassword("digdigdig");
        supervisor2.setDepartment("Science Naturelle");
        supervisor2.setMatricule("47854");

        Supervisor supervisor3 = new Supervisor();
        supervisor3.setId(3L);
        supervisor3.setLastName("Olive");
        supervisor3.setFirstName("Peter");
        supervisor3.setEmail("olvp@gmail.com");
        supervisor3.setPassword("jobgobdob");
        supervisor3.setDepartment("Informatique");
        supervisor3.setMatricule("23478");

        supervisorRepository.saveAll(Arrays.asList(supervisor1, supervisor2, supervisor3));
    }

    @Test
    public void testFindAll() {
        int actual = supervisorRepository.findAll().size();

        assertEquals(actual, 3);
    }

    @Test
    public void testCreate_withValidSupervisor() {
        Supervisor supervisor = new Supervisor();
        supervisor.setLastName("Trap");
        supervisor.setFirstName("Moose");
        supervisor.setEmail("tram@gmail.com");
        supervisor.setPassword("piecesofcheese");
        supervisor.setDepartment("Batiment");
        supervisor.setMatricule("02834");

        Optional<Supervisor> actual = supervisorService.create(supervisor);
        assertTrue(actual.isPresent());
    }

    @Test
    public void testCreate_withNullSupervisor() {
        Optional<Supervisor> actual = supervisorService.create(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetByID_withValidID() {
        Long validID = 1L;
        Optional<Supervisor> actual = supervisorService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetByID_withNullID() {
        Optional<Supervisor> actual = supervisorService.getOneByID(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetAll() {
        int expectedLength = 3;

        List<Supervisor> supervisorList = supervisorService.getAll();

        assertEquals(expectedLength, supervisorList.size());
    }

    @Test
    public void testUpdate_withValidEntries() {
        Supervisor supervisor = new Supervisor();
        supervisor.setLastName("Trap");
        supervisor.setFirstName("Moose");
        supervisor.setEmail("tram@gmail.com");
        supervisor.setPassword("piecesofcheese");
        supervisor.setDepartment("Batiment");
        supervisor.setMatricule("02834");
        Long validID = 2L;

        Optional<Supervisor> actual = supervisorService.update(supervisor, validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testUpdate_withNullEntries() {
        Supervisor supervisor = new Supervisor();
        supervisor.setLastName("Candle");
        supervisor.setFirstName("Tea");
        supervisor.setEmail("cant@outlook.com");
        supervisor.setPassword("cantPass");
        supervisor.setDepartment("info");

        Optional<Supervisor> actual = supervisorService.update(supervisor, null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testDelete_withValidID() {
        Long validId = 1L;

        boolean actual = supervisorService.deleteByID(validId);

        assertTrue(actual);
    }

    @Test
    public void testDelete_withNullID() {
        boolean actual = supervisorService.deleteByID(null);

        assertFalse(actual);
    }

    @Test
    public void testFindSupervisorByEmailAndPassword() {
        String email = "keyh@gmail.com";
        String password = "galaxy29";

        Supervisor supervisor = supervisorRepository.findSupervisorByEmailAndPassword(email, password);
        String actual = supervisor.getFirstName();

        assertEquals(actual, "Harold");
    }

    @Test
    public void testExistsByEmailAndPassword_withValidEntries() {
        String email = "keyh@gmail.com";
        String password = "galaxy29";

        boolean actual = supervisorRepository.existsByEmailAndPassword(email, password);

        assertTrue(actual);
    }

    @Test
    public void testExistsByEmailAndPassword_withNullEntries() {
        boolean actual = supervisorRepository.existsByEmailAndPassword(null, null);

        assertFalse(actual);
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() {
        String email = "keyh@gmail.com";
        String password = "galaxy29";

        Optional<Supervisor> actual = supervisorService.getOneByEmailAndPassword(email, password);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEntries() {
        Optional<Supervisor> actual = supervisorService.getOneByEmailAndPassword(null, null);

        assertTrue(actual.isEmpty());
    }
}
