package com.gestionnaire_de_stage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceTest {

    @Autowired
    private AuthService authService;


    @MockBean
    private MockMvc mockMc;

    @Test
    @DisplayName("Test login valide pour Gestionnaire")
    public void test_managerLogin_valid(){
        String email = "";
        String password = "";
        assertNotNull(authService.loginManager(email, password));
    }
}
