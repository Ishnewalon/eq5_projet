package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Notification;
import com.gestionnaire_de_stage.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private final CurriculumService curriculumService;

    private final NotificationRepository notificationRepository;

    public NotificationService(CurriculumService curriculumService, NotificationRepository notificationRepository){
        this.curriculumService = curriculumService;
        this.notificationRepository = notificationRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfCurriculumValidation(Curriculum curriculum) throws IdDoesNotExistException {
        Curriculum previous = curriculumService.getOneByID(curriculum.getId());
        if (curriculum.getIsValid() != previous.getIsValid()) {
            notificationRepository.save(new Notification(
                    curriculum.getStudent(),
                    "Votre curriculum : " + curriculum.getName() + " à été vérifié. Veuillez voir son état dans vos Curriculums.")
            );
        }
    }
}
