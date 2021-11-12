package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.CurriculumNotValidException;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testStudentSignUp_withValidEntries() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.create(any())).thenReturn(dummyStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Student actualStudent = MAPPER.readValue(response.getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(dummyStudent);
    }


    @Test
    public void testStudentSignUp_withNullStudent() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.create(any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le courriel ne peut pas être null");
    }

    @Test
    public void testStudentSignUp_withInvalidStudent() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.create(any())).thenThrow(StudentAlreadyExistsException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Ce courriel existe déjà!");
    }

    @Test
    public void testStudentLogin_withValidEntries() throws Exception {
        Student dummyStudent = getDummyStudent();
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenReturn(dummyStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Student actualStudent = MAPPER.readValue(response.getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualStudent.getLastName()).isEqualTo("Brawl");
    }

    @Test
    public void testStudentLogin_withNullEntries() throws Exception {
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Le courriel et le mot de passe ne peuvent pas être null");
    }

    @Test
    public void testStudentLogin_withInvalidEntries() throws Exception {
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    @Test
    public void testSetPrincipalCurriculum_withValidEntries() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any())).thenReturn(student);
        student.setPrincipalCurriculum(curriculum);
        when(studentService.setPrincipalCurriculum(any(), any())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/setprincipal/" + student.getId() + "/" + curriculum.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("changer");
    }

    @Test
    void testSetPrincipalCurriculum_withNullEntries() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any())).thenThrow(new IllegalArgumentException("ID est null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/setprincipal/" + student.getId() + "/" + curriculum.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("null");
    }

    @Test
    void testSetPrincipalCurriculum_withEntriesNotExists() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/setprincipal/" + student.getId() + "/" + curriculum.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Les IDs ne peuvent pas être null");
    }

    @Test
    void testSetPrincipalCurriculum_CurriculumInvalid() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any())).thenReturn(student);
        when(studentService.setPrincipalCurriculum(any(), any())).thenThrow(CurriculumNotValidException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/setprincipal/" + student.getId() + "/" + curriculum.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le curriculum doit etre valide");
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student());
        when(studentService.getAll()).thenReturn(Arrays.asList(new Student(), new Student()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Student>>() {
                })).isEqualTo(list);

    }

    @Test
    public void testGetAllStudentsNotAssigned() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student());
        when(studentService.getAllUnassignedStudents()).thenReturn(Arrays.asList(new Student(), new Student()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/needAssignement")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Student> actualStudentList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualStudentList).isEqualTo(list);
    }

    @Test
    public void testGetAllStudentWithoutCv() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student());
        when(studentService.getAllStudentWithoutCv()).thenReturn(Arrays.asList(new Student(), new Student()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/no-cv")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Student> actualStudentList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualStudentList).isEqualTo(list);
    }


    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Brawl");
        dummyStudent.setFirstName("Spaghetta");
        dummyStudent.setPhone("514-546-2375");
        dummyStudent.setEmail("clip@gmail.com");
        dummyStudent.setPassword("thiswilldo");
        dummyStudent.setAddress("758 George");
        dummyStudent.setCity("LaSalle");
        dummyStudent.setDepartment("Informatique");
        dummyStudent.setPostalCode("H5N 9F2");
        dummyStudent.setMatricule("1740934");

        return dummyStudent;
    }

    private Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setName("myFileeee");
        curriculum.setType("pdf");
        curriculum.setId(1L);
        return curriculum;
    }
}
