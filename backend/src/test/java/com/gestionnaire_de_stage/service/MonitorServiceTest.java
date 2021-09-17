package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.User;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @Autowired
    private MonitorRepository monitorRepository;

    @BeforeAll
    public void insertData(){
        User user = new Monitor();

    }
}
