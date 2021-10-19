package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import com.gestionnaire_de_stage.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void testStudentSignUp_withValidEntries() throws Exception {
        Student student = getStudent();
        when(studentService.create(any())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(student))).andReturn();

        var actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(student);
    }


    @Test
    public void testStudentSignUp_withNullStudent() throws Exception {
        Student student = getStudent();
        when(studentService.create(any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(student))).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel ne peut pas être null");
    }

    @Test
    public void testStudentSignUp_withInvalidStudent() throws Exception {
        Student student = getStudent();
        when(studentService.create(any())).thenThrow(StudentAlreadyExistsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/student/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(student))).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Ce courriel existe déjà!");
    }

    @Test
    public void testStudentLogin_withValidEntries() throws Exception {
        Student student = getStudent();
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var actualStudent = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualStudent.getLastName()).isEqualTo("Brawl");
    }

    @Test
    public void testStudentLogin_withNullEntries() throws Exception {
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Le courriel et le mot de passe ne peuvent pas être null");
    }

    @Test
    public void testStudentLogin_withInvalidEntries() throws Exception {
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenThrow(EmailAndPasswordDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Erreur: Courriel ou Mot de Passe Invalide");
    }

    private Student getStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setLastName("Brawl");
        student.setFirstName("Spaghetta");
        student.setPhone("514-546-2375");
        student.setEmail("clip@gmail.com");
        student.setPassword("thiswilldo");
        student.setAddress("758 George");
        student.setCity("LaSalle");
        student.setDepartment("Informatique");
        student.setPostalCode("H5N 9F2");
        student.setMatricule("1740934");

        return student;
    }
}
