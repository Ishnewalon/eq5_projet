package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.model.Notification;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Test
    public void testGetAllByUser() throws Exception {
        when(notificationService.getAllByUserId(any())).thenReturn(getDummyNotificationList());

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/notification/all/{userId}", getDummyStudent().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).hasSizeGreaterThan(0);
    }

    @Test
    public void testGetAllByUser_throwsIllegalArg() throws Exception {
        when(notificationService.getAllByUserId(any())).thenThrow(new IllegalArgumentException("test"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/notification/all/{userId}", getDummyStudent().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("test");
    }

    private List<Notification> getDummyNotificationList() {
        return List.of(
                getDummyNotification(),
                getDummyNotification(),
                getDummyNotification());
    }

    private Notification getDummyNotification() {
        return new Notification(
                getDummyStudent(),
                "a test message"
        );
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Winter");
        dummyStudent.setFirstName("Summer");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }
}
