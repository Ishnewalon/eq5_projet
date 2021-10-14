package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        student.setId(1L);
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
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.create(null);
        });
    }

    @Test
    public void testCreate_alreadyExistsStudent() {
        when(studentRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(StudentAlreadyExistsException.class, () -> {
            studentService.create(getStudent());
        });
    }

    @Test
    public void testGetByID_withValidID() throws Exception{
        Long validID = 1L;
        Student student = getStudent();
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.getById(any())).thenReturn(student);

        Optional<Student> actual = studentService.getOneByID(validID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.getOneByID(null);
        });
    }

    @Test
    public void testGetByID_doesntExistID() {
        Student student = getStudent();
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            studentService.getOneByID(student.getId());
        });
    }

    @Test
    public void testGetAll() {
        when(studentRepository.findAll()).thenReturn(getListOfStudents());

        final List<Student> allStudents = studentService.getAll();

        assertThat(allStudents.size()).isEqualTo(3);
        assertThat(allStudents.get(0).getFirstName()).isEqualTo("Tea");
    }

    private List<Student> getListOfStudents() {
        List<Student> studentList = new ArrayList<>();
        Long idIterator = 1L;
        for (int i = 0; i < 3; i++) {
            Student student = getStudent();
            student.setId(idIterator);
            studentList.add(student);
            idIterator++;
        }
        return studentList;
    }

    @Test
    public void testUpdate_withValidEntries() throws Exception {
        Student student = getStudent();
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(student);

        Optional<Student> actual = studentService.update(student, student.getId());

        assertTrue(actual.isPresent());
    }

    @Test
    public void testUpdate_withNullID() {
        Student student = getStudent();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.update(student, null);
        });
    }

    @Test
    public void testUpdate_withNullStudent() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.update(null, 1L);
        });
    }

    @Test
    public void testUpdate_doesntExistID() {
        Student student = getStudent();
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            studentService.update(student, student.getId());
        });
    }

    @Test
    public void testDelete_withValidID() {
        Long id = 1L;
        when(studentRepository.existsById(any())).thenReturn(true);
        doNothing().when(studentRepository).deleteById(any());

       studentService.deleteByID(id);

        verify(studentRepository, times(0)).deleteById(any());
    }


    @Test
    public void testDelete_withNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.deleteByID(null);
        });
    }
/*
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
*/
}
