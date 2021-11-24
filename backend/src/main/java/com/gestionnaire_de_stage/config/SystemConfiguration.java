package com.gestionnaire_de_stage.config;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.service.OfferApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Clock;
import java.util.Date;

@Configuration
@Slf4j(topic = "SystemConfiguration")
@EnableScheduling
public class SystemConfiguration {

    private final OfferApplicationService offerApplicationService;

    public SystemConfiguration(OfferApplicationService offerApplicationService) {
        this.offerApplicationService = offerApplicationService;
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    void updateRegurlarlyOfferApplicationsStatus() {
        log.debug("Time before updating offerApplications {}", new Date());
        int numRecordsModified = offerApplicationService.updateAllOfferApplicationThatWereInAInterviewStatusFromStatusToOther(Status.EN_ATTENTE_ENTREVUE, Status.EN_ATTENTE_REPONSE);
        log.info("Updated status of {} offerApplications at {}", numRecordsModified, new Date());
    }
}
