package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.SessionAlreadyExistException;
import com.gestionnaire_de_stage.exception.SessionDoesNotExistException;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(Session session) throws Exception {
        Assert.isTrue(session.getTypeSession() != null, "Le type de session est obligatoire");
        Assert.isTrue(session.getYear() != null, "L'année est obligatoire");
        if (sessionRepository.existsByTypeSessionAndYear(session.getTypeSession(), session.getYear()))
            throw new SessionAlreadyExistException("Une Session existe déjà!");

        session.setId(null);

        return sessionRepository.save(session);
    }
}
