package com.gestionnaire_de_stage.listener;

import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.service.BeanService;
import com.gestionnaire_de_stage.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferApplicationListenerTest {

    @InjectMocks
    private OfferApplicationListener offerApplicationListener;

    @Mock
    private BeanService beanService;

    @Mock
    private NotificationService notificationService;

    @Test
    void postPersistTest() {
        doNothing().when(notificationService).notifyOfNewApplicant(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            offerApplicationListener.postPersist(new OfferApplication());

            verify(notificationService, times(1)).notifyOfNewApplicant(any());
        }
    }

    @Test
    void postPersistTest_catchesException() {
        doThrow(IllegalArgumentException.class).when(notificationService).notifyOfNewApplicant(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> offerApplicationListener.postPersist(new OfferApplication()));
        }
    }

    @Test
    void preUpdateTest() throws Exception {
        doNothing().when(notificationService).notifyOfOfferAppStatusSetToStageTrouve(any());
        doNothing().when(notificationService).notifyOfOfferAppInterviewSet(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            offerApplicationListener.preUpdate(new OfferApplication());

            verify(notificationService, times(1)).notifyOfOfferAppStatusSetToStageTrouve(any());
            verify(notificationService, times(1)).notifyOfOfferAppInterviewSet(any());
        }
    }

    @Test
    void preUpdateTest_catchesException() throws Exception {
        doThrow(IllegalArgumentException.class).when(notificationService).notifyOfOfferAppStatusSetToStageTrouve(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> offerApplicationListener.preUpdate(new OfferApplication()));
        }
    }
}
