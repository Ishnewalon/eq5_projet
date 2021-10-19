package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CurriculumController.class)
public class CurriculumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurriculumService curriculumService;

    @Test
    public void uploadCurriculumTest_withValidEntries() throws Exception {
        Curriculum curriculum = getCurriculum();
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        Long studentId = 1L;
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenReturn(curriculum);
        when(curriculumService.create(any())).thenReturn(curriculum);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/curriculum/upload")
                .file(file)
                .param("id", new ObjectMapper().writeValueAsString(studentId)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void uploadCurriculumTest_withInvalidStudentID() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        Long studentId = 1L;
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/curriculum/upload")
                .file(file)
                .param("id", new ObjectMapper().writeValueAsString(studentId)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Invalid Student ID");
    }

    @Test
    public void uploadCurriculumTest_fileThrowsIOException() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        Long studentId = 1L;
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenThrow(IOException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/curriculum/upload")
                .file(file)
                .param("id", new ObjectMapper().writeValueAsString(studentId)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("IO Error: check file integrity!");
    }

    private Curriculum getCurriculum() {
        Student student = new Student();
        student.setId(1L);

        return new Curriculum(
                "file",
                "content type",
                "test".getBytes(),
                student
        );
    }
}
