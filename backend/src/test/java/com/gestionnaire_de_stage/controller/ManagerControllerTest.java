package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import com.gestionnaire_de_stage.service.ManagerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private ManagerRepository managerRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Manager getDummyManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    @BeforeEach
    public void init(){
        managerRepository.save(getDummyManager());
    }

    @AfterEach
    public void each(){
        managerRepository.deleteAll();
    }

    @Test
    public void testManagerLogin_withValidEntries() throws Exception {
        Manager manager = getDummyManager();
        String email = manager.getEmail();
        String password = manager.getPassword();
    //    when(managerService.getOneByEmailAndPassword(email, password)).thenReturn(Optional.of(manager));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/manager/%s/%s", email, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Manager resultGetManagerLogin = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Manager.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultGetManagerLogin.getLastName()).isEqualTo("Kably");
    }

    @Test
    public void testManagerLogin_withNullEntries() throws Exception {
        String email = null;
        String password = null;
     //   when(managerService.getOneByEmailAndPassword(email, password)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/manager/%s/%s", email, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Manager resultGetManagerLogin = null;
        try {
            resultGetManagerLogin = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Manager.class);
        }catch (Exception ignored){
        }

        assertThat(resultGetManagerLogin).isEqualTo(null);
    }
}
