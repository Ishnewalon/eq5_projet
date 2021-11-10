package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SessionAlreadyExistException;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private Clock clock;

    private Clock fixedClock;


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

    @Test
    public void testGetActualAndFutureSessions() {
        List<Session> dummySessions = getDummySessions();

        fixedClock = Clock.fixed(LocalDate.of(Year.now().getValue(), 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(sessionRepository.findAllByYearGreaterThanEqual(any())).thenReturn(dummySessions);

        List<Session> actualAndFutureSessions = sessionService.getActualAndFutureSessions();

        assertThat(actualAndFutureSessions).containsAll(dummySessions);
    }

    @Test
    public void testGetActualAndFutureSessions_withoutWinterSessionOfCurrentYear() {
        List<Session> dummySessions = getDummySessions();
        fixedClock = Clock.fixed(LocalDate.of(Year.now().getValue(), 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(sessionRepository.findAllByYearGreaterThanEqual(any())).thenReturn(dummySessions);

        List<Session> actualAndFutureSessions = sessionService.getActualAndFutureSessions();

        assertThat(actualAndFutureSessions).containsExactlyInAnyOrder(dummySessions.get(1), dummySessions.get(2), dummySessions.get(3), dummySessions.get(4));
    }

    @Test
    public void testGetActualAndFutureSessions_getOnlyFutureSessions() {
        List<Session> dummySessions = getDummySessions();
        fixedClock = Clock.fixed(LocalDate.of(Year.now().getValue(), 10, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(sessionRepository.findAllByYearGreaterThanEqual(any())).thenReturn(dummySessions);

        List<Session> actualAndFutureSessions = sessionService.getActualAndFutureSessions();

        assertThat(actualAndFutureSessions).containsAll(dummySessions);
    }

    @Test
    void testGetOneBySessionId() throws IdDoesNotExistException {
        Session session = new Session(1L, TypeSession.HIVER, Year.now());
        when(sessionRepository.existsById(any())).thenReturn(true);
        when(sessionRepository.getById(any())).thenReturn(session);

        Session sessionFound = sessionService.getOneBySessionId(session.getId());

        assertThat(sessionFound).isEqualTo(session);
    }

    @Test
    void testGetOneBySessionId_whenSessionNonExistent() {
        when(sessionRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> sessionService.getOneBySessionId(1L));
    }

    private List<Session> getDummySessions() {
        return List.of(
                new Session(1L, TypeSession.HIVER, Year.now()),
                new Session(2L, TypeSession.ETE, Year.now()),
                new Session(3L, TypeSession.HIVER, Year.now().plusYears(1)),
                new Session(4L, TypeSession.ETE, Year.now().plusYears(1)),
                new Session(5L, TypeSession.HIVER, Year.now().plusYears(2)));
    }
}
