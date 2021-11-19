package com.gestionnaire_de_stage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SystemConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
