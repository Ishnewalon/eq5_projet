package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepository.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManagerServiceTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    private Manager getDummyManager(){
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setName("Kably");
        manager.setNumTel("5143643320");
        return manager;
    }

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        managerRepository.save(getDummyManager());
    }

    @AfterEach
    public void after(){
        managerRepository.deleteAll();
    }

    @Test
    public void testFindall(){
        Manager manager = getDummyManager();
        manager.setId(1L);
        assertThat(managerRepository.findAll()).hasSize(1).contains(manager);
    }

    @Test
    public void testCreate_withNullManager() throws Exception {
        Optional<Manager> manager = Optional.empty();
        try {
            manager = managerService.create(null);
        }catch (Exception e){
            fail(e);
        }
        assertTrue(manager.isEmpty());
    }

    @Test
    public void testCreate_withValidManager(){
        Optional<Manager> manager = Optional.empty();
        try {
            manager = managerService.create(getDummyManager());
        }catch (Exception e){
            fail(e);
        }
        assertFalse(manager.isEmpty());
    }

}