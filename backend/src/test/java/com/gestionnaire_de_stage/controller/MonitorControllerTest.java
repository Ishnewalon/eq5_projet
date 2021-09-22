package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import com.gestionnaire_de_stage.service.MonitorService;
import com.gestionnaire_de_stage.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@WebMvcTest(MonitorController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitorService monitorService;

    @MockBean
    private MonitorRepository monitorRepository;

    @MockBean
    private StudentService studentService;

    @Test
    public void monitorSignupTest() throws Exception {
        Monitor monitor = new Monitor();
        monitor.setEmail("potato@mail.com");
        monitor.setPassword("secretPasswordShhhh");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(monitor))).andReturn();

        Monitor actual = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Monitor.class);
        System.out.println(actual);
    }
}
