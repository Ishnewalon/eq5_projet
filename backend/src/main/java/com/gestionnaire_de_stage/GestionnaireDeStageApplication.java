package com.gestionnaire_de_stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gestionnaire_de_stage.*")
public class GestionnaireDeStageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionnaireDeStageApplication.class, args);
    }

}
