package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CurriculumController.class)
class CurriculumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurriculumService curriculumService;

    @Test
    public void testGetAllStudents_withInvalidCurriculum() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student(), new Student());
        when(curriculumService.findAllStudentsWithCurriculumNotValidatedYet()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/invalid/students")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Student>>() {
                })).isEqualTo(list);

    }

    @Test
    public void testGetAllStudents_withInvalidCurriculum_withEmptyList() throws Exception {
        when(curriculumService.findAllStudentsWithCurriculumNotValidatedYet()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/invalid/students")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Student>>() {
                })).isEqualTo(Collections.emptyList());

    }

    @Test
    public void testValidate() throws Exception {
        Long id = 1L;
        when(curriculumService.validate(anyLong())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/validate/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual).contains("Succes: curriculum valide!");
    }

    @Test
    public void testValidate_whenCvNonExistent() throws Exception {
        Long id = 1L;
        when(curriculumService.validate(anyLong())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/validate/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum non existant!");
    }

    @Test
    public void testValidate_whenCurriculumAlreadyTreated() throws Exception {
        Long id = 1L;
        when(curriculumService.validate(anyLong())).thenThrow(CurriculumAlreadyTreatedException.class);

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/validate/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum deja traite!");
    }

    @Test
    public void testValidate_withIdCurriculumNull() throws Exception {
        Long id = 1L;
        when(curriculumService.validate(anyLong())).thenThrow(new IllegalArgumentException("Erreur: Le id du curriculum ne peut pas etre null"));

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/validate/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: Le id du curriculum ne peut pas etre null");
    }

    @Test
    public void testReject() throws Exception {
        Long id = 1L;
        when(curriculumService.reject(anyLong())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/reject/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual).contains("Succes: curriculum rejete!");
    }

    @Test
    public void testReject_whenCvNonExistent() throws Exception {
        Long id = 1L;
        when(curriculumService.reject(anyLong())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/reject/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum non existant!");
    }

    @Test
    public void testReject_whenCurriculumAlreadyTreated() throws Exception {
        Long id = 1L;
        when(curriculumService.reject(anyLong())).thenThrow(CurriculumAlreadyTreatedException.class);

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/reject/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum deja traite!");
    }

    @Test
    public void testReject_withIdCurriculumNull() throws Exception {
        Long id = 1L;
        when(curriculumService.reject(anyLong())).thenThrow(new IllegalArgumentException("Erreur: Le id du curriculum ne peut pas etre null"));

        MvcResult mvcResult = mockMvc.perform(post(String.format("/curriculum/reject/%s", id))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: Le id du curriculum ne peut pas etre null");
    }
}