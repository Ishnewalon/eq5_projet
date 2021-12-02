package com.gestionnaire_de_stage.listener;

import com.gestionnaire_de_stage.model.Offer;
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
public class OfferListenerTest {

    @InjectMocks
    private OfferListener offerListener;

    @Mock
    private BeanService beanService;

    @Mock
    private NotificationService notificationService;

    @Test
    void postPersistTest() {
        doNothing().when(notificationService).notifyOfNewOffer(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            offerListener.postPersist(new Offer());

            verify(notificationService, times(1)).notifyOfNewOffer(any());
        }
    }

    @Test
    void postPersistTest_catchesException() {
        doThrow(IllegalArgumentException.class).when(notificationService).notifyOfNewOffer(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> offerListener.postPersist(new Offer()));
        }
    }

    @Test
    void preUpdateTest() {
        doNothing().when(notificationService).notifyOfOfferValidation(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            offerListener.preUpdate(new Offer());

            verify(notificationService, times(1)).notifyOfOfferValidation(any());
        }
    }

    @Test
    void preUpdateTest_catchesException() {
        doThrow(IllegalArgumentException.class).when(notificationService).notifyOfOfferValidation(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> offerListener.preUpdate(new Offer()));
        }
    }
}
