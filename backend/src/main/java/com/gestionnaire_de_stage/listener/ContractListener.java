package com.gestionnaire_de_stage.listener;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.service.BeanService;
import com.gestionnaire_de_stage.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PreUpdate;

public class ContractListener {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumListener.class);

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }

    @PreUpdate
    public void preUpdate(Contract contract) {
        try {
            getNotificationService().notifyOfContractSignature(contract);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }
}
