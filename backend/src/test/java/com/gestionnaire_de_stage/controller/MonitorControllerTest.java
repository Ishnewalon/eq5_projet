package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import com.gestionnaire_de_stage.service.MonitorService;
import com.gestionnaire_de_stage.service.StudentService;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

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
    public void monitorSignupTest_withValidEntries() throws Exception {
        Monitor monitor = getMonitor();
        when(monitorService.create(any())).thenReturn(monitor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(monitor))).andReturn();

        var actualMonitor = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Monitor.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualMonitor).isEqualTo(monitor);
    }

    @Test
    public void monitorSignupTest_withNullEntries() throws Exception {
        //when(monitorService.create(null)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(null))).andReturn();
        int actual = mvcResult.getResponse().getStatus();

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual);
    }

    @Test
    public void monitorSignupTest_withInvalidEntries() throws Exception {
        Monitor monitor = new Monitor();
        monitor.setEmail("notAnEmail");
        monitor.setPassword("2short");
        //when(monitorService.create(monitor)).thenReturn(Optional.of(monitor));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/monitor/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(monitor))).andReturn();
        int actual = mvcResult.getResponse().getStatus();

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual);
    }
    
    @Test
    public void monitorLoginTest_withValidEntries() throws Exception {
        Monitor monitor = new Monitor();
        monitor.setEmail("potato@mail.com");
        monitor.setPassword("secretPasswordShhhh");
        monitor.setFirstName("toto");
        monitor.setLastName("tata");
        monitor.setDepartment("Informatique");
        String email = monitor.getEmail();
        String password = monitor.getPassword();
        //when(monitorService.getOneByEmailAndPassword(email, password)).thenReturn(Optional.of(monitor));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/monitor/potato@mail.com/secretPasswordShhhh")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Monitor actual = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Monitor.class);

        assertEquals(monitor, actual);
    }

    @Test
    public void monitorLoginTest_withNullEntries() throws Exception {
        //when(monitorService.getOneByEmailAndPassword(null, null)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/monitor/null/null")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int actual = mvcResult.getResponse().getStatus();

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual);
    }
    private Monitor getMonitor() {
        Monitor monitor = new Monitor();
        monitor.setEmail("potato@mail.com");
        monitor.setPassword("secretPasswordShhhh");
        monitor.setFirstName("toto");
        monitor.setLastName("tata");
        monitor.setDepartment("Informatique");
        return monitor;
    }


}
