package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.SessionAlreadyExistException;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(Session session) throws Exception {
        Assert.isTrue(session.getTypeSession() != null, "Le type de session est obligatoire");
        Assert.isTrue(session.getDateDebut() != null, "La date de début est obligatoire");
        Assert.isTrue(session.getDateFin() != null, "La date de fin est obligatoire");
        Assert.isTrue(session.getDateDebut().isBefore(session.getDateFin()), "La date de début doit être antérieure à la date de fin");
        if (sessionRepository.existsByDateDebutBetweenOrDateFinBetween(session.getDateDebut(), session.getDateFin(), session.getDateDebut(), session.getDateFin()))
            throw new SessionAlreadyExistException("Une Session existe déjà!");

        return sessionRepository.save(session);
    }
}
