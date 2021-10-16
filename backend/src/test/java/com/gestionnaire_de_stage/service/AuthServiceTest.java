package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private ManagerService managerService;

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    @Test
    @Order(1)
    @DisplayName("Test login invalide pour Manager")
    public void test_managerLogin_invalid(){
        String email = "";
        String password = "";
        assertNull(authService.loginManager(email, password));
    }

 /*   @Test
    @Order(2)
    @DisplayName("Test login valide pour Manager")
    public void test_managerLogin_valid(){
        String email = "oussamakably@gmail.com";
        String password = "Test1234";
        Optional<Manager> manager = Optional.empty();

        try{
            manager = managerService.create(getDummyManager());
        }catch (Exception e){
            fail(e);
        }

        assertFalse(manager.isEmpty());
        assertNotNull(authService.loginManager(email, password));
    }*/

}
