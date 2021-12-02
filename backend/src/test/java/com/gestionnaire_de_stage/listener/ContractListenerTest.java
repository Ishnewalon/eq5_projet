package com.gestionnaire_de_stage.listener;

import com.gestionnaire_de_stage.model.Contract;
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
public class ContractListenerTest {

    @InjectMocks
    private ContractListener contractListener;

    @Mock
    private BeanService beanService;

    @Mock
    private NotificationService notificationService;

    @Test
    void preUpdateTest() throws Exception {
        doNothing().when(notificationService).notifyOfContractSignature(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            contractListener.preUpdate(new Contract());

            verify(notificationService, times(1)).notifyOfContractSignature(any());
        }
    }

    @Test
    void preUpdateTest_catchesException() throws Exception {
        doThrow(IllegalArgumentException.class).when(notificationService).notifyOfContractSignature(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> contractListener.preUpdate(new Contract()));
        }
    }
}
