package com.gestionnaire_de_stage;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Environment environment;

    @MockBean
    private ManagerRepository managerRepository;

    @BeforeAll
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Order(1)
    public void emptyManagerRepositoryTest(){
        //Act
        List<Manager> managers = managerRepository.findAll();

        //Arrange
        assertTrue(managers.isEmpty());
    }

    @Test
    @Order(2)
    public void createManagerTest(){
        Manager manager = new Manager();

    }
}