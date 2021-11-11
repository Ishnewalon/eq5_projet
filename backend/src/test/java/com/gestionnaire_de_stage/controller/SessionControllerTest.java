package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.SessionAlreadyExistException;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.service.SessionService;
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

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(SessionController.class)
public class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    private final ObjectMapper MAPPER = new ObjectMapper();


    @Test
    public void testCreateSession() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        Session session = new Session(1L, TypeSession.HIVER, Year.now());
        when(sessionService.createSession(any())).thenReturn(session);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/sessions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(session)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Session créée avec succès!");
    }


    @Test
    public void testCreateSession_whenSessionAlreadyExist() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        Session session = new Session(1L, TypeSession.HIVER, Year.now());
        when(sessionService.createSession(any())).thenThrow(new SessionAlreadyExistException("Une Session existe déjà!"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/sessions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(session)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Une Session existe déjà!");
    }

    @Test
    public void testCreateSession_withTypeSessionNull() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        Session session = new Session(1L, null, Year.now());
        when(sessionService.createSession(any())).thenThrow(new IllegalArgumentException("Le type de session est obligatoire"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/sessions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(session)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le type de session est obligatoire");
    }


    @Test
    public void testCreateSession_withYearNull() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        Session session = new Session(1L, TypeSession.ETE, null);
        when(sessionService.createSession(any())).thenThrow(new IllegalArgumentException("L'année est obligatoire"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/sessions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(session)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'année est obligatoire");
    }

    @Test
    public void testGetActualAndFutureSessions() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        List<Session> sessions = List.of(new Session(1L, TypeSession.HIVER, Year.of(2022)), new Session(2L, TypeSession.ETE, Year.of(2022)), new Session(3L, TypeSession.ETE, Year.of(2023)));
        when(sessionService.getActualAndFutureSessions()).thenReturn(sessions);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/sessions")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Session> returnedSessions = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedSessions).isEqualTo(sessions);
    }

}
