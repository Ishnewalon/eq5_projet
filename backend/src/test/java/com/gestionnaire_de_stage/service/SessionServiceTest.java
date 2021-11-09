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
        LocalDate dateDebut = LocalDate.now().minusDays(1);
        LocalDate dateFin = LocalDate.now().plusDays(1);
        Session session = new Session(1L, TypeSession.HIVER, dateDebut, dateFin);
        when(sessionRepository.existsByDateDebutBetweenOrDateFinBetween(any(), any(), any(), any())).thenReturn(false);
        when(sessionRepository.save(any())).thenReturn(session);

        Session sessionCreated = sessionService.createSession(session);

        assertThat(sessionCreated).isEqualTo(session);
    }

    @Test
    public void testCreateSession_whenSessionAlreadyExist() {
        Session session = new Session(1L, TypeSession.HIVER, LocalDate.now(), LocalDate.now().plusDays(1));
        when(sessionRepository.existsByDateDebutBetweenOrDateFinBetween(any(), any(), any(), any())).thenReturn(true);

        assertThrows(SessionAlreadyExistException.class,
                () -> sessionService.createSession(session), "La session existe déjà");
    }

    @Test
    public void testCreateSession_whenTypeNull() {
        Session session = new Session(1L, null, LocalDate.now(), LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> sessionService.createSession(session), "Le type de session est obligatoire");
    }

    @Test
    public void testCreateSession_whenDateDebutNull() {
        Session session = new Session(1L, TypeSession.ETE, null, LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> sessionService.createSession(session), "La date de début est obligatoire");
    }

    @Test
    public void testCreateSession_whenDateFinNull() {
        Session session = new Session(1L, TypeSession.ETE, LocalDate.now(), null);

        assertThrows(IllegalArgumentException.class,
                () -> sessionService.createSession(session), "La date de fin est obligatoire");
    }
    @Test
    public void testCreateSession_whenDateDebutIsNotBeforeDateFin() {
        Session session = new Session(1L, TypeSession.ETE, LocalDate.now(), LocalDate.now().minusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> sessionService.createSession(session), "La date de début doit être antérieure à la date de fin");
    }
}
