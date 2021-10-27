package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(StageController.class)
public class StageControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testCreateContractSignManager_withValidEntries() throws Exception {
        Student dummyStudent = getDummyStudent();
        Stage dummyStage = getDummyStage();
       // when(stageService.createContract(any())).thenReturn(dummyStage);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stage/signContract/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).contains("Contrat signer avec succes");
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Brawl");
        dummyStudent.setFirstName("Spaghetta");
        dummyStudent.setPhone("514-546-2375");
        dummyStudent.setEmail("clip@gmail.com");
        dummyStudent.setPassword("thiswilldo");
        dummyStudent.setAddress("758 George");
        dummyStudent.setCity("LaSalle");
        dummyStudent.setDepartment("Informatique");
        dummyStudent.setPostalCode("H5N 9F2");
        dummyStudent.setMatricule("1740934");

        return dummyStudent;
    }

    private Stage getDummyStage() {
        Stage dummyStage = new Stage();
        dummyStage.setId(1L);
        dummyStage.setFilePath("C:/home");
        dummyStage.setStudent(getDummyStudent());

        return dummyStage;
    }
}
