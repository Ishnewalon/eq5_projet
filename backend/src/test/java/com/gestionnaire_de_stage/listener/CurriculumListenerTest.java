package com.gestionnaire_de_stage.listener;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
import com.gestionnaire_de_stage.service.BeanService;
import com.gestionnaire_de_stage.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurriculumListenerTest {

    @InjectMocks
    private CurriculumListener curriculumListener;

    @Mock
    private BeanService beanService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private CurriculumRepository curriculumRepository;

    @Test
    void preUpdateTest() throws Exception {
        doNothing().when(notificationService).notifyOfCurriculumValidation(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            curriculumListener.preUpdate(new Curriculum());

            verify(notificationService, times(1)).notifyOfCurriculumValidation(any());
        }
    }

    @Test
    void preUpdateTest_throwsException() throws Exception {
        doThrow(IdDoesNotExistException.class).when(notificationService).notifyOfCurriculumValidation(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> curriculumListener.preUpdate(new Curriculum()));
        }
    }

}
