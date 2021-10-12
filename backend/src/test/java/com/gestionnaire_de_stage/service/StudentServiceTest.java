package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Test
    public void testCreate_withValidStudent() throws Exception{
        Student student = getStudent();
        when(studentRepository.save(any())).thenReturn(student);

        Optional<Student> actual = studentService.create(student);

        assertTrue(actual.isPresent());
    }

    private Student getStudent() {
        Student student = new Student();
        student.setLastName("Candle");
        student.setFirstName("Tea");
        student.setEmail("cant@outlook.com");
        student.setPassword("cantPass");
        student.setDepartment("info");
        student.setMatricule("4673943");
        return student;
    }

    @Test
    public void testCreate_withNullStudent() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            studentService.create(null);
        });
    }

    @Test
    public void testGetByID_withValidID() {
        Long validID = 1L;

        Optional<Student> actual = studentService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetByID_withNullID() {
        Optional<Student> actual = studentService.getOneByID(null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetAll() {
        int expectedLength = 3;

        List<Student> studentList = studentService.getAll();

        assertEquals(expectedLength, studentList.size());
    }

    @Test
    public void testUpdate_withValidEntries(){
        Student student = new Student();
        student.setLastName("Candle");
        student.setFirstName("Tea");
        student.setEmail("cant@outlook.com");
        student.setPassword("cantPass");
        student.setDepartment("info");
        Long validID = 2L;

        Optional<Student> actual = studentService.update(student, validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testUpdate_withNullEntries() {
        Student student = new Student();
        student.setLastName("Candle");
        student.setFirstName("Tea");
        student.setEmail("cant@outlook.com");
        student.setPassword("cantPass");
        student.setDepartment("info");

        Optional<Student> actual = studentService.update(student, null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testDelete_withValidID() {
        Long validId = 1L;

        boolean actual = studentService.deleteByID(validId);

        assertTrue(actual);
    }

    @Test
    public void testDelete_withNullID() {
        boolean actual = studentService.deleteByID(null);

        assertFalse(actual);
    }

    @Test
    public void testFindStudentByEmailAndPassword() {
       String email = "jscotty@gmail.com";
       String password = "rockrocks";

       Student student = studentRepository.findStudentByEmailAndPassword(email, password);
       String actual = student.getFirstName();

       assertEquals(actual, "Jordan");
    }

    @Test
    public void testExistsByEmailAndPassword_withValidEntries() {
        String email = "jscotty@gmail.com";
        String password = "rockrocks";

        boolean actual = studentRepository.existsByEmailAndPassword(email, password);

        assertTrue(actual);
    }

    @Test
    public void testExistsByEmailAndPassword_withNullEntries() {
        boolean actual = studentRepository.existsByEmailAndPassword(null, null);

        assertFalse(actual);
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() {
        String email = "jscotty@gmail.com";
        String password = "rockrocks";

        Optional<Student> actual = studentService.getOneByEmailAndPassword(email, password);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEntries() {
        Optional<Student> actual = studentService.getOneByEmailAndPassword(null, null);

        assertTrue(actual.isEmpty());
    }

}
