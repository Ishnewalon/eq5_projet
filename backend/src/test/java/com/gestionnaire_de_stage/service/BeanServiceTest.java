package com.gestionnaire_de_stage.service;

import org.hibernate.resource.beans.container.internal.NoSuchBeanException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class BeanServiceTest {

    @InjectMocks
    private BeanService beanService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ApplicationContext applicationContext;

    @BeforeEach
    void init() {
        beanService.setApplicationContext(applicationContext);
    }

    @Test
    void testGetBean() {
        doReturn(notificationService).when(applicationContext)
                .getBean((Class<?>) any(Class.class));

        NotificationService actual = BeanService.getBean(NotificationService.class);

        assertThat(actual).isInstanceOf(NotificationService.class);
    }

    @Test
    void testGetBean_withNullParam() {
        assertThrows(IllegalArgumentException.class,
                () -> BeanService.getBean(null));
    }

    @Test
    void testGetBean_throwsBeanException() {
        doThrow(NoSuchBeanException.class).when(applicationContext)
                .getBean((Class<?>) any(Class.class));

        assertThrows(NoSuchBeanException.class,
                () -> BeanService.getBean(String.class));
    }
}
