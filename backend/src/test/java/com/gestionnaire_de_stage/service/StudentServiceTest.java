package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Test
    public void testCreate_withValidStudent() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.save(any())).thenReturn(dummyStudent);

        Student actualStudent = studentService.create(dummyStudent);

        assertThat(actualStudent.getFirstName()).isEqualTo(dummyStudent.getFirstName());
    }


    @Test
    public void testCreate_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.create(null));
    }

    @Test
    public void testCreate_alreadyExistsStudent() {
        when(studentRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(StudentAlreadyExistsException.class,
                () -> studentService.create(getDummyStudent()));
    }

    @Test
    public void testGetByID_withValidID() throws Exception {
        Long validID = 1L;
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.getById(any())).thenReturn(dummyStudent);

        Student actualStudent = studentService.getOneByID(validID);

        assertThat(actualStudent.getEmail()).isEqualTo(dummyStudent.getEmail());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> studentService.getOneByID(dummyStudent.getId()));
    }

    @Test
    public void testGetAll() {
        when(studentRepository.findAll()).thenReturn(getDummyStudentList());

        final List<Student> actualStudentList = studentService.getAll();

        assertThat(actualStudentList.size()).isEqualTo(3);
        assertThat(actualStudentList.get(0).getFirstName()).isEqualTo("Tea");
    }


    @Test
    public void testUpdate_withValidEntries() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(dummyStudent);

        Student actualStudent = studentService.update(dummyStudent, dummyStudent.getId());

        assertThat(actualStudent.getEmail()).isEqualTo(dummyStudent.getEmail());
    }

    @Test
    public void testUpdate_withNullID() {
        Student dummyStudent = getDummyStudent();

        assertThrows(IllegalArgumentException.class,
                () -> studentService.update(dummyStudent, null));
    }

    @Test
    public void testUpdate_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.update(null, 1L));
    }

    @Test
    public void testUpdate_doesntExistID() {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> studentService.update(dummyStudent, dummyStudent.getId()));
    }

    @Test
    public void testDelete_withValidID() throws Exception {
        Long id = 1L;
        when(studentRepository.existsById(any())).thenReturn(true);
        doNothing().when(studentRepository).deleteById(any());

        studentService.deleteByID(id);

        verify(studentRepository, times(1)).deleteById(any());
    }


    @Test
    public void testDelete_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.deleteByID(null));
    }

    @Test
    public void testDelete_doesntExistID() {
        Long id = 1L;
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> studentService.deleteByID(id));
    }

    @Test
    public void testStudentByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsByEmailAndPassword(any(), any())).thenReturn(true);
        when(studentRepository.findStudentByEmailAndPassword(any(), any())).thenReturn(dummyStudent);

        Student actualStudent = studentService.getOneByEmailAndPassword(dummyStudent.getEmail(), dummyStudent.getPassword());

        assertThat(actualStudent.getFirstName()).isEqualTo(dummyStudent.getFirstName());
    }

    @Test
    public void testStudentByEmailAndPassword_withNullEmail() {
        Student dummyStudent = getDummyStudent();

        assertThrows(IllegalArgumentException.class,
                () -> studentService.getOneByEmailAndPassword(null, dummyStudent.getPassword()));
    }

    @Test
    public void testStudentByEmailAndPassword_withNullPassword() {
        Student dummyStudent = getDummyStudent();

        assertThrows(IllegalArgumentException.class,
                () -> studentService.getOneByEmailAndPassword(dummyStudent.getEmail(), null));
    }

    @Test
    public void testStudentByEmailAndPassword_doesntExistEmailAndPassword() {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class,
                () -> studentService.getOneByEmailAndPassword(dummyStudent.getEmail(), dummyStudent.getPassword()));
    }

    @Test
    public void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(getDummyStudentList());

        List<Student> studentList = studentService.getAll();

        assertThat(studentList.size()).isEqualTo(3);
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Candle");
        dummyStudent.setFirstName("Tea");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }

    private List<Student> getDummyStudentList() {
        List<Student> dummyStudentList = new ArrayList<>();
        Long idIterator = 1L;
        for (int i = 0; i < 3; i++) {
            Student dummyStudent = getDummyStudent();
            dummyStudent.setId(idIterator);
            dummyStudentList.add(dummyStudent);
            idIterator++;
        }
        return dummyStudentList;
    }
}
