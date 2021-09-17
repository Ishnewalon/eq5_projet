package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonitorServiceTest {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitorService monitorService;

    @BeforeAll
    public void insertData(){
        Monitor monitor1 = new Monitor();
        monitor1.setId(1L);
        monitor1.setFirstName("Steph");
        monitor1.setName("Kazuk");
        monitor1.setEmail("stepotato@gmail.com");
        monitor1.setNumTel("5145555112");
        monitor1.setDepartment("Informatique");

        Monitor monitor2 = new Monitor();
        monitor2.setId(2L);
        monitor2.setFirstName("Ouss");
        monitor2.setName("ama");
        monitor2.setEmail("ouste@gmail.com");
        monitor2.setNumTel("5145555112");
        monitor2.setDepartment("Informatique");

        Monitor monitor3 = new Monitor();
        monitor3.setId(3L);
        monitor3.setFirstName("same");
        monitor3.setName("dude");
        monitor3.setEmail("dudesame@gmail.com");
        monitor3.setNumTel("5145555112");
        monitor3.setDepartment("Informatique");

        monitorRepository.saveAll(Arrays.asList(monitor1, monitor2, monitor3));
    }

    @Test
    public void testFindAll(){
        int actual = monitorRepository.findAll().size();

        assertEquals(3, actual);
    }

    @Test
    public void testSignup(){
        Monitor monitor4 = new Monitor();
        monitor4.setFirstName("Test");

        Monitor actual = monitorService.signup(monitor4);

        assertNotNull(monitorService.signup(monitor4));
    }

}
