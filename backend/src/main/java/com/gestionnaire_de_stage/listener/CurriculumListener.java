package com.gestionnaire_de_stage.listener;


import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.service.BeanService;
import com.gestionnaire_de_stage.service.NotificationService;

import javax.persistence.PreUpdate;

public class CurriculumListener {

    @PreUpdate
    public void preUpdate(Curriculum curriculum) {
        try {
            getNotificationService().notifyOfCurriculumValidation(curriculum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }
}
