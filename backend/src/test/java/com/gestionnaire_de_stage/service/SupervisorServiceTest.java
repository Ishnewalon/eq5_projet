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
        supervisor1.setName("Keys");
        supervisor1.setFirstName("Harold");
        supervisor1.setEmail("keyh@gmail.com");
        supervisor1.setPassword("galaxy29");
        supervisor1.setDepartment("Comptabilité");
        supervisor1.setMatricule("04736");

        Supervisor supervisor2 = new Supervisor();
        supervisor2.setId(2L);
        supervisor2.setName("Hole");
        supervisor2.setFirstName("Van");
        supervisor2.setEmail("holv@gmail.com");
        supervisor2.setPassword("digdigdig");
        supervisor2.setDepartment("Science Naturelle");
        supervisor2.setMatricule("47854");

        Supervisor supervisor3 = new Supervisor();
        supervisor3.setId(3L);
        supervisor3.setName("Olive");
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
        supervisor.setName("Trap");
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
}
