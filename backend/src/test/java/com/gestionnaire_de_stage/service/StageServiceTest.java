package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.repository.StageRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StageServiceTest {

    @InjectMocks
    StageService stageService;

    @Mock
    StageRepository stageRepository;
}
