package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CurriculumController.class)
class CurriculumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurriculumService curriculumService;
    @Test
    public void testGetAllStudents_withInvalidCurriculum() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student(),new Student());
        when(curriculumService.findAllStudentsWithCurriculumNotValidatedYet()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/invalid/students")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Student>>(){})).isEqualTo(list);

    }
    @Test
    public void testGetAllStudents_withInvalidCurriculum_withEmptyList() throws Exception {
        when(curriculumService.findAllStudentsWithCurriculumNotValidatedYet()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/invalid/students")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Student>>(){})).isEqualTo(Collections.emptyList());

    }
}