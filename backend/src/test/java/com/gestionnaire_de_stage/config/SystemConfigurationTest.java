package com.gestionnaire_de_stage.config;

import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SystemConfigurationTest {

    @SpyBean
    private SystemConfiguration systemConfiguration;

    @Test
    @Disabled
    public void testUpdateRegurlarlyOfferApplicationsStatus() {
        final ThrowingRunnable runnable = () -> verify(systemConfiguration, atLeast(1)).updateRegurlarlyOfferApplicationsStatus();
        await().untilAsserted(runnable);
    }
}