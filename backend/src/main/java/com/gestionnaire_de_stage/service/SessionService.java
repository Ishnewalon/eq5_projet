package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SessionAlreadyExistException;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final Clock clock;

    public SessionService(SessionRepository sessionRepository, Clock clock) {
        this.sessionRepository = sessionRepository;
        this.clock = clock;
    }

    public Session createSession(Session session) throws Exception {
        Assert.isTrue(session.getTypeSession() != null, "Le type de session est obligatoire");
        Assert.isTrue(session.getYear() != null, "L'année est obligatoire");
        if (sessionRepository.existsByTypeSessionAndYear(session.getTypeSession(), session.getYear()))
            throw new SessionAlreadyExistException("Une Session existe déjà!");

        session.setId(null);

        return sessionRepository.save(session);
    }

    public List<Session> getActualAndFutureSessions() {
        int monthValue = LocalDate.now(clock).getMonthValue();
        if (monthValue >= Month.SEPTEMBER.getValue())
            return sessionRepository.findAllByYearGreaterThanEqual(Year.now().plusYears(1));

        List<Session> sessions = sessionRepository.findAllByYearGreaterThanEqual(Year.now());
        if (monthValue > Month.MAY.getValue())
            sessions = removeWinterSessionOfThisYear(sessions);
        return sessions;
    }

    private List<Session> removeWinterSessionOfThisYear(List<Session> sessions) {
        return sessions.stream()
                .filter(session -> !(session.getTypeSession() == TypeSession.HIVER && session.getYear().equals(Year.now())))
                .collect(Collectors.toList());
    }

    public Session getOneBySessionId(Long idSession) throws IdDoesNotExistException {
        Assert.isTrue(idSession != null, "L'id de la session est obligatoire");
        if (!sessionRepository.existsById(idSession))
            throw new IdDoesNotExistException();
        return sessionRepository.getById(idSession);
    }
}
