package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ManagerController.class)
@SuppressWarnings("ConstantConditions")
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerService managerService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testManagerLogin_withValidEntries() throws Exception {
        Manager dummyManager = getDummyManager();
        String email = "oussamakably@gmail.com";
        String password = "Test1234";
        when(managerService.getOneByEmailAndPassword(any(), any())).thenReturn(dummyManager);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/manager/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Manager actualSupervisor = MAPPER.readValue(response.getContentAsString()
                , Manager.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualSupervisor.getLastName()).isEqualTo("Kably");
    }

    @Test
    public void testManagerLogin_withNullEntries() throws Exception {
        String email = null;
        String password = null;
        when(managerService.getOneByEmailAndPassword(any(),any())).thenThrow(new IllegalArgumentException("Courriel et mot de passe ne peuvent pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/manager/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel et mot de passe ne peuvent pas être vide");
    }

    @Test
    public void testSupervisorLogin_withInvalidEntries() throws Exception {
        String email = "qerw@qwetq.com";
        String password = "qwreqwer";
        when(managerService.getOneByEmailAndPassword(any(),any())).thenThrow(new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalid"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/manager/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel ou mot de passe invalid");
    }

    private Manager getDummyManager() {
        Manager dummyManager = new Manager();
        dummyManager.setPassword("Test1234");
        dummyManager.setEmail("oussamakablsy@gmail.com");
        dummyManager.setFirstName("Oussama");
        dummyManager.setLastName("Kably");
        dummyManager.setPhone("5143643320");
        return dummyManager;
    }
}
