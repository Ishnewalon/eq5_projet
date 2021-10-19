package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
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
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual).contains("Succes: curriculum valide!");
    }

    @Test
    public void testValidate_whenCvNonExistent() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum non existant!");
    }

    @Test
    public void testValidate_whenCurriculumAlreadyTreated() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(CurriculumAlreadyTreatedException.class);

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum deja traite!");
    }

    @Test
    public void testValidate_withIdCurriculumNull() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(null, true);
        when(curriculumService.validate(any(), anyBoolean())).thenThrow(new IllegalArgumentException("Erreur: Le id du curriculum ne peut pas etre null"));

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: Le id du curriculum ne peut pas etre null");
    }

    @Test
    public void testReject() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual).contains("Succes: curriculum rejete!");
    }

    @Test
    public void testReject_whenCvNonExistent() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum non existant!");
    }

    @Test
    public void testReject_whenCurriculumAlreadyTreated() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(CurriculumAlreadyTreatedException.class);

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum deja traite!");
    }

    @Test
    public void testReject_withIdCurriculumNull() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(null, false);
        when(curriculumService.validate(any(), anyBoolean())).thenThrow(new IllegalArgumentException("Erreur: Le id du curriculum ne peut pas etre null"));

        MvcResult mvcResult = mockMvc.perform(post("/curriculum/validate")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(validationCurriculum)
                )).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: Le id du curriculum ne peut pas etre null");
    }

    @Test
    public void testGetCurriculumById() throws Exception {
        String text = "some xml";
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setData(text.getBytes());

        when(curriculumService.findOneById(anyLong())).thenReturn(dummyCurriculum);

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/download/{id}", dummyCurriculum.getId())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn();

        var actual = mvcResult.getResponse();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual.getContentAsString()).isEqualTo(text);
        assertThat(actual.getHeader(HttpHeaders.CONTENT_DISPOSITION)).contains(dummyCurriculum.getName());
    }

    @Test
    public void testGetCurriculumById_withIdNull() throws Exception {
        Long id = null;
        when(curriculumService.findOneById(any())).thenThrow(new IllegalArgumentException("Erreur: L'id du curriculum ne peut pas etre null!"));

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/download/{idCurriculum}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: L'id du curriculum ne peut pas etre null!");
    }
    @Test
    public void testGetCurriculumById_withCurriculumNonExistant() throws Exception {
        Long id = 34L;
        when(curriculumService.findOneById(any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(get("/curriculum/download/{idCurriculum}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual).contains("Erreur: curriculum non existant!");
    }

    Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setName("myFileeee");
        curriculum.setType("pdf");
        curriculum.setId(1L);
        return curriculum;
    }
}