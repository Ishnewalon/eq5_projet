package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.SessionAlreadyExistException;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Test
    public void testCreateSession() throws Exception {
        Session session = new Session(1L, TypeSession.HIVER, Year.now());
        when(sessionRepository.existsByTypeSessionAndYear(any(), any())).thenReturn(false);
        when(sessionRepository.save(any())).thenReturn(session);

        Session sessionCreated = sessionService.createSession(session);

        assertThat(sessionCreated).isEqualTo(session);
    }

    @Test
    public void testCreateSession_whenSessionAlreadyExist() {
        Session session = new Session(1L, TypeSession.HIVER, Year.now());
        when(sessionRepository.existsByTypeSessionAndYear(any(), any())).thenReturn(true);

        assertThrows(SessionAlreadyExistException.class,
                () -> sessionService.createSession(session), "La session existe déjà");
    }

    @Test
    public void testCreateSession_whenTypeNull() {
        Session session = new Session(1L, null, Year.now());

        assertThrows(IllegalArgumentException.class,
                () -> sessionService.createSession(session), "Le type de session est obligatoire");
    }

    @Test
    public void testCreateSession_whenYearNull() {
        Session session = new Session(1L, TypeSession.ETE, null);

        assertThrows(IllegalArgumentException.class,
                () -> sessionService.createSession(session), "L'année est obligatoire");
    }
}
