package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.StudentCurriculumsDTO;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.CurriculumUsedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import com.gestionnaire_de_stage.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(CurriculumController.class)
public class CurriculumControllerTest {

    private final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CurriculumService curriculumService;
    @MockBean
    private StudentService studentService;

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
        assertThat(response.getContentAsString()).contains("Le curriculum a ??t?? t??l??vers?? avec succ??s");
    }

    @Test
    public void uploadCurriculumTest_withInvalidStudentID() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'??tudiant associ?? ?? cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'??tudiant associ?? ?? cet identifiant");
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
        assertThat(response.getContentAsString()).contains("Erreur de t??l??versement. R??essayer plus tard");
    }

    @Test
    public void uploadCurriculumTest_studentIdThrowsIdDoesNotExistException() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.convertMultipartFileToCurriculum(any(), any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'??tudiant associ?? ?? cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'??tudiant associ?? ?? cet identifiant");
    }

    @Test
    public void uploadCurriculumTest_idStudentNull() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.create(any())).thenThrow(new IllegalArgumentException("L'??tudiant ne peut pas ??tre vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'??tudiant ne peut pas ??tre vide");
    }

    @Test
    public void uploadCurriculumTest_idCurriculumNull() throws Exception {
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(curriculumService.create(any())).thenThrow(new IllegalArgumentException("L'??tudiant ne peut pas ??tre vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/curriculum/upload")
                                .file(file)
                                .param("id", MAPPER.writeValueAsString(studentId)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'??tudiant ne peut pas ??tre vide");
    }

    @Test
    public void testGetAllCurriculumByStudentId() throws Exception {
        Long studentId = 1L;
        List<Curriculum> dummyCurriculumList = getDummyCurriculumList();
        when(curriculumService.findAllByStudentId(any())).thenReturn(dummyCurriculumList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/student/{0}", studentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Curriculum> returnedCurriculum = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedCurriculum).containsAll(dummyCurriculumList);
    }

    @Test
    public void testGetAllCurriculumByStudentId_withNullId() throws Exception {
        Long studentId = 1L;
        when(curriculumService.findAllByStudentId(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas d'??tudiant associ?? ?? cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/student/{0}", studentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'??tudiant associ?? ?? cet identifiant");
    }

    @Test
    public void testValidate() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(any())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Curriculum valid??!");
    }

    @Test
    public void testValidate_whenCvNonExistent() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas de curriculum associ?? ?? cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de curriculum associ?? ?? cet identifiant");
    }

    @Test
    public void testValidate_whenCurriculumAlreadyTreated() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, true);
        when(curriculumService.validate(any())).thenThrow(new CurriculumAlreadyTreatedException("Ce curriculum a d??j?? ??t?? trait??"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Ce curriculum a d??j?? ??t?? trait??");
    }

    @Test
    public void testValidate_withIdCurriculumNull() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(null, true);
        when(curriculumService.validate(any()))
                .thenThrow(new IllegalArgumentException("L'identifiant du curriculum ne peut pas ??tre vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant du curriculum ne peut pas ??tre vide");
    }

    @Test
    public void testReject() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(any())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Curriculum rejet??!");
    }

    @Test
    public void testReject_whenCvNonExistent() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas de curriculum associ?? ?? cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de curriculum associ?? ?? cet identifiant");
    }

    @Test
    public void testReject_whenCurriculumAlreadyTreated() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(1L, false);
        when(curriculumService.validate(any())).thenThrow(new CurriculumAlreadyTreatedException("Ce curriculum a d??j?? ??t?? trait??"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Ce curriculum a d??j?? ??t?? trait??");
    }

    @Test
    public void testReject_withIdCurriculumNull() throws Exception {
        ValidationCurriculum validationCurriculum = new ValidationCurriculum(null, false);
        when(curriculumService.validate(any()))
                .thenThrow(new IllegalArgumentException("L'identifiant du curriculum ne peut pas ??tre vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/curriculum/validate")
                                .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(validationCurriculum)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant du curriculum ne peut pas ??tre vide");
    }

    @Test
    public void testAllCurriculumsByStudentAsStudentCurriculumsDTO_withValidEntries() throws Exception {
        Student student = getDummyStudent();
        StudentCurriculumsDTO studentCurriculumsDTO = getDummyStudentCurriculumsDTO();
        when(studentService.getOneByID(any()))
                .thenReturn(student);
        when(curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(any()))
                .thenReturn(studentCurriculumsDTO);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/all_student/{studentID}", student.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final StudentCurriculumsDTO actual = MAPPER.readValue(response.getContentAsString(), StudentCurriculumsDTO.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual.getPrincipal().getId()).isEqualTo(studentCurriculumsDTO.getPrincipal().getId());
    }

    @Test
    public void testAllCurriculumsByStudentAsStudentCurriculumsDTO_withNullStudent() throws Exception {
        Student student = getDummyStudent();
        when(studentService.getOneByID(any()))
                .thenReturn(student);
        when(curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(any()))
                .thenThrow(new IllegalArgumentException("L'etudiant ne peut pas ??tre vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/all_student/{studentID}", student.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'etudiant ne peut pas ??tre vide");
    }

    @Test
    public void testAllCurriculumsByStudentAsStudentCurriculumsDTO_withIdNotFound() throws Exception {
        Student student = getDummyStudent();
        when(studentService.getOneByID(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas d'??tudiant associ?? ?? cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/curriculum/all_student/{studentID}", student.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'??tudiant associ?? ?? cet identifiant");
    }

    @Test
    public void testDeleteOneById() throws Exception {
        Curriculum curriculum = getDummyCurriculum();
        doNothing().when(curriculumService).deleteOneById(any());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/curriculum/delete/{curriculumId}", curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Curriculum effac??");
    }

    @Test
    void testDeleteOneById_throwsIllegalArg() throws Exception {
        Curriculum curriculum = getDummyCurriculum();
        doThrow(new IllegalArgumentException("No null values!"))
                .when(curriculumService)
                .deleteOneById(any());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/curriculum/delete/{curriculumId}", curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("No null values!");
    }

    @Test
    void testDeleteOneById_throwsCurriculumUsedException() throws Exception {
        Curriculum curriculum = getDummyCurriculum();
        doThrow(new CurriculumUsedException("Impossible de supprimer."))
                .when(curriculumService)
                .deleteOneById(any());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/curriculum/delete/{curriculumId}", curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Impossible de supprimer.");
    }

    private StudentCurriculumsDTO getDummyStudentCurriculumsDTO() {
        return new StudentCurriculumsDTO(getDummyCurriculum(), getDummyCurriculumList());
    }

    private List<Curriculum> getDummyCurriculumList() {
        Curriculum dummyCurriculum1 = getDummyCurriculum();
        Curriculum dummyCurriculum2 = getDummyCurriculum();
        Curriculum dummyCurriculum3 = getDummyCurriculum();

        return Arrays.asList(dummyCurriculum1, dummyCurriculum2, dummyCurriculum3);
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

    Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setName("myFileeee");
        curriculum.setType("pdf");
        curriculum.setId(1L);
        return curriculum;
    }
}