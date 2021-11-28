package com.gestionnaire_de_stage.listener;


import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.service.BeanService;
import com.gestionnaire_de_stage.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PreUpdate;

public class CurriculumListener {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumListener.class);

    @PreUpdate
    public void preUpdate(Curriculum curriculum) {
        try {
            getNotificationService().notifyOfCurriculumValidation(curriculum);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }
}
