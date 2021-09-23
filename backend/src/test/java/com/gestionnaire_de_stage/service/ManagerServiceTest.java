package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepository.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManagerServiceTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setName("Kably");
        manager.setNumTel("5143643320");
        return manager;
    }

    @BeforeEach
    public void init() {
        managerRepository.save(getDummyManager());
    }

    @AfterEach
    public void after() {
        managerRepository.deleteAll();
    }

    @Test
    public void testFindall() {
        Manager manager = getDummyManager();
        manager.setId(1L);
        assertThat(managerRepository.findAll()).hasSize(1).contains(manager);
    }

    @Test
    public void testCreate_withNullManager() throws Exception {
        Optional<Manager> manager = Optional.empty();
        try {
            manager = managerService.create(null);
        } catch (Exception e) {
            fail(e);
        }
        assertTrue(manager.isEmpty());
    }

    @Test
    public void testCreate_withValidManager() {
        Optional<Manager> manager = Optional.empty();
        try {
            manager = managerService.create(getDummyManager());
        } catch (Exception e) {
            fail(e);
        }
        assertFalse(manager.isEmpty());
    }

    @Test
    public void testGetByID_withValidID() {
        Long validID = 1L;

        Optional<Manager> actual = managerService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetByID_withNullID() {
        Optional<Manager> actual = managerService.getOneByID(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetAll() {
        int expectedLength = 3;

        List<Manager> monitorList = managerService.getAll();

        assertEquals(expectedLength, monitorList.size());
    }

    @Test
    public void testUpdate_withValidEntries() {
        Manager manager = getDummyManager();
        Long validID = 2L;

        Optional<Manager> actual = Optional.empty();
        try {
            actual = managerService.update(manager, validID);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(actual.isPresent());
    }

    @Test
    public void testUpdate_withNullEntries() {
        Manager manager = getDummyManager();

        Optional<Manager> actual = Optional.empty();
        try {
            actual = managerService.update(manager, null);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testDelete_withValidID() {
        Long validID = 1L;

        boolean actual = managerService.deleteByID(validID);

        assertTrue(actual);
    }

    @Test
    public void testDelete_withNullID() {
        boolean actual = managerService.deleteByID(null);

        assertFalse(actual);
    }

    @Test
    public void testFindMonitorByEmailAndPassword() {
        String email = "stepotato@gmail.com";
        String password = "testPassword";

        Optional<Manager> manager = managerRepository.findManagerByEmailAndPassword(email, password);
        assertTrue(manager.isPresent());
        Manager m = null;
        try{
            m = manager.orElseThrow();
            assertEquals(m.getFirstName(), "Steph");
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    public void testExistsByEmailAndPassword_withValidEntries() {
        String email = "stepotato@gmail.com";
        String password = "testPassword";

        boolean actual = managerRepository.findManagerByEmailAndPassword(email, password).isPresent();

        assertTrue(actual);
    }

    @Test
    public void testExistsByEmailAndPassword_withNullEntries() {
        boolean actual = managerRepository.findManagerByEmailAndPassword(null, null).isPresent();

        assertFalse(actual);
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() {
        String email = "stepotato@gmail.com";
        String password = "testPassword";

        Optional<Manager> actual = managerService.getOneByEmailAndPassword(email, password);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEntries() {
        Optional<Manager> actual = managerService.getOneByEmailAndPassword(null, null);

        assertTrue(actual.isEmpty());
    }
}