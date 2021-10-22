package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(CurriculumController.class)
public class CurriculumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurriculumService curriculumService;
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void uploadCurriculumTest_withValidEntries() throws Exception {
        Long studentId = 1L;
        Curriculum dummyCurriculum = getDummyCurriculum();
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenReturn(dummyCurriculum);
        when(curriculumService.create(any())).thenReturn(dummyCurriculum);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).contains("File Uploaded Successfully");
    }

    @Test
    public void uploadCurriculumTest_withInvalidStudentID() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Invalid Student ID");
    }

    @Test
    public void uploadCurriculumTest_fileThrowsIOException() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenThrow(IOException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("IO Error: check file integrity!");
    }
    @Test
    public void uploadCurriculumTest_studentIdThrowsIdDoesNotExistException() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.create(any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Invalid Student ID");
    }
    @Test
    public void uploadCurriculumTest_idStudentNull() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.create(any())).thenThrow(new IllegalArgumentException("L'étudiant est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'étudiant est null");
    }
    @Test
    public void uploadCurriculumTest_idCurriculumNull() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.create(any())).thenThrow(new IllegalArgumentException("L'étudiant est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'étudiant est null");
    }


    @Test
    public void testGetAllCurriculumNotValidatedYet_withValidList() throws Exception {
        List<Curriculum> list = Arrays.asList(new Curriculum(), new Curriculum(), new Curriculum());
        when(curriculumService.findAllCurriculumNotValidatedYet()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/invalid/students")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Curriculum> actualCurriculumList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualCurriculumList).isEqualTo(list);

    }

    @Test
    public void testGetAllCurriculumNotValidatedYet_withEmptyList() throws Exception {
        when(curriculumService.findAllCurriculumNotValidatedYet()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/invalid/students")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Curriculum> actualCurriculumList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualCurriculumList).isEqualTo(Collections.emptyList());

    }

    @Test
    public void testGetAllCurriculumValidated_withValidList() throws Exception {
        Curriculum cv1 = new Curriculum();
        Curriculum cv2 = new Curriculum();
        Curriculum cv3 = new Curriculum();
        cv1.setIsValid(true);
        cv2.setIsValid(true);
        cv3.setIsValid(true);
        List<Curriculum> list = Arrays.asList(cv1, cv2, cv3);
        when(curriculumService.findAllCurriculumValidated()).thenReturn(list);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/valid/students")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Curriculum> actualCurriculumList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualCurriculumList).isEqualTo(list);

    }

    @Test
    public void testGetAllCurriculumValidated_withEmptyList() throws Exception {
        when(curriculumService.findAllCurriculumValidated()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/valid/students")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Curriculum> actualCurriculumList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualCurriculumList).isEqualTo(Collections.emptyList());

    }

    @Test
    public void testValidate() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Curriculum validé!");
    }

    @Test
    public void testValidate_whenCvNonExistent() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Curriculum non existant!");
    }

    @Test
    public void testValidate_whenCurriculumAlreadyTreated() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(CurriculumAlreadyTreatedException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Curriculum déjà traité!");
    }

    @Test
    public void testValidate_withIdCurriculumNull() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(null, true);
        when(curriculumService.validate(any(), anyBoolean()))
                .thenThrow(new IllegalArgumentException("Le id du curriculum ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du curriculum ne peut pas être null");
    }

    @Test
    public void testReject() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Curriculum rejeté!");
    }

    @Test
    public void testReject_whenCvNonExistent() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Curriculum non existant!");
    }

    @Test
    public void testReject_whenCurriculumAlreadyTreated() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(anyLong(), anyBoolean())).thenThrow(CurriculumAlreadyTreatedException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Curriculum déjà traité!");
    }

    @Test
    public void testReject_withIdCurriculumNull() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(null, false);
        when(curriculumService.validate(any(), anyBoolean()))
                .thenThrow(new IllegalArgumentException("Le id du curriculum ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du curriculum ne peut pas être null");
    }

    @Test
    public void testGetCurriculumById() throws Exception {
        String text = "some xml";
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setData(text.getBytes());

        when(curriculumService.findOneById(anyLong())).thenReturn(dummyCurriculum);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/download/{id}", dummyCurriculum.getId())
                                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(text);
        assertThat(response.getHeader(HttpHeaders.CONTENT_DISPOSITION)).contains(dummyCurriculum.getName());
    }

    @Test
    public void testGetCurriculumById_withIdNull() throws Exception {
        Long id = null;
        when(curriculumService.findOneById(any()))
                .thenThrow(new IllegalArgumentException("Le id du curriculum ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/download/{idCurriculum}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du curriculum ne peut pas être null");
    }

    @Test
    public void testGetCurriculumById_withCurriculumNonExistant() throws Exception {
        Long id = 34L;
        when(curriculumService.findOneById(any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/download/{idCurriculum}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Curriculum non existant!");
    }

    Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setName("myFileeee");
        curriculum.setType("pdf");
        curriculum.setId(1L);
        return curriculum;
    }
}