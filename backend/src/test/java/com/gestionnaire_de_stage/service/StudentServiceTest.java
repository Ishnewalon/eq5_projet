package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;
    @Mock
    CurriculumRepository curriculumRepository;
    @Mock
    CurriculumService curriculumService;

    @Test
    public void testCreate_withValidStudent() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsByEmail(any())).thenReturn(false);
        when(studentRepository.existsByMatricule(any())).thenReturn(false);
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
    public void testCreate_alreadyExistsMatricule() {
        when(studentRepository.existsByMatricule(any())).thenReturn(true);

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

        Student actualStudent = studentService.update(dummyStudent);

        assertThat(actualStudent.getEmail()).isEqualTo(dummyStudent.getEmail());
    }


    @Test
    @SuppressWarnings("ConstantConditions")
    public void testUpdate_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.update(null));
    }

    @Test
    public void testUpdate_doesntExistID() {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> studentService.update(dummyStudent));
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

    @Test
    void testSetPrincipalCurriculum() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentRepository.getById(any())).thenReturn(student);
        when(studentRepository.existsById(any())).thenReturn(true);
        when(curriculumService.getOneById(any())).thenReturn(curriculum);
        student.setPrincipalCurriculum(curriculum);
        when(studentRepository.save(any())).thenReturn(student);

        Student actualStudent = studentService.setPrincipalCurriculum(getDummyStudent(), curriculum.getId());

        assertThat(actualStudent.getPrincipalCurriculum()).isEqualTo(curriculum);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testSetPrincipalCurriculum_withStudentNull() {
        Curriculum dummyCurriculum = getDummyCurriculum();

        assertThrows(IllegalArgumentException.class,
                () -> studentService.setPrincipalCurriculum(null, dummyCurriculum.getId()));
    }

    @Test
    void testSetPrincipalCurriculum_withIdCurriculumNull() {
        Student dummyStudent = getDummyStudent();

        assertThrows(IllegalArgumentException.class,
                () -> studentService.setPrincipalCurriculum(dummyStudent, null));
    }

    @Test
    void testSetPrincipalCurriculum_CurriculumNonExistant() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentRepository.existsById(any())).thenReturn(true);
        when(curriculumService.getOneById(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class,
                () -> studentService.setPrincipalCurriculum(student, curriculum.getId()));
    }

    @Test
    void testSetPrincipalCurriculum_StudentNonExistant() {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> studentService.setPrincipalCurriculum(student, curriculum.getId()));
    }

    @Test
    void testSetPrincipalCurriculum_CurriculumInvalid() throws IdDoesNotExistException {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setIsValid(null);
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.getById(any())).thenReturn(student);
        when(curriculumService.getOneById(any())).thenReturn(curriculum);

        assertThrows(CurriculumNotValidException.class,
                () -> studentService.setPrincipalCurriculum(student, curriculum.getId()));
    }

    @Test
    public void testGetAllUnassignedStudents() {
        List<Student> dummyStudentList = getDummyStudentList();
        when(studentRepository.getAllByPrincipalCurriculum_IsValidAndSupervisorNull(true)).thenReturn(dummyStudentList);

        List<Student> actualStudentList = studentService.getAllUnassignedStudents();

        assertThat(actualStudentList.size()).isEqualTo(dummyStudentList.size());
    }

    @Test
    void testAssign_withUnassignedStudent() {
        Student dummyStudent = getDummyStudent();
        Supervisor dummySupervisor = getDummySupervisor();
        when(studentRepository.save(any())).thenReturn(dummyStudent);

        boolean isAssigned = studentService.assign(dummyStudent, dummySupervisor);

        assertThat(isAssigned).isTrue();
    }

    @Test
    void testAssign_withAssignedStudent() {
        Student dummyStudent = getDummyStudent();
        Supervisor dummySupervisor = getDummySupervisor();
        dummyStudent.setSupervisor(dummySupervisor);

        boolean isAssigned = studentService.assign(dummyStudent, dummySupervisor);

        assertThat(isAssigned).isFalse();
    }

    @Test
    public void testGetOneByEmail_withValidEmail() throws DoesNotExistException {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsByEmail(any())).thenReturn(true);
        when(studentRepository.getByEmail(any())).thenReturn(dummyStudent);

        Student actual = studentService.getOneByEmail(dummyStudent.getEmail());

        assertThat(actual).isEqualTo(dummyStudent);
    }

    @Test
    public void testGetOneByEmail_withNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.getOneByEmail(null));
    }

    @Test
    public void testGetOneByEmail_withInvalidEmail() {
        String email = "civfan@email.com";
        assertThrows(DoesNotExistException.class,
                () -> studentService.getOneByEmail(email),
                "L'email n'existe pas");
    }

    @Test
    public void testChangePassword() throws IdDoesNotExistException {
        Student dummyStudent = getDummyStudent();
        when(studentRepository.existsById(any())).thenReturn(true);
        when(studentRepository.getById(any())).thenReturn(dummyStudent);
        when(studentRepository.save(any())).thenReturn(dummyStudent);

        Student actual = studentService.changePassword(dummyStudent.getId(), "newPassword");

        assertThat(actual.getPassword()).isEqualTo("newPassword");
        assertThat(actual.getId()).isEqualTo(dummyStudent.getId());
    }


    @Test
    public void testChangePassword_withInvalidId() {
        assertThrows(IdDoesNotExistException.class,
                () -> studentService.changePassword(1L, "newPassword"));
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


    private Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setId(1L);
        curriculum.setIsValid(true);
        curriculum.setData("test".getBytes());
        curriculum.setName("filename");
        curriculum.setName("pdffff");
        return curriculum;
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

    private Supervisor getDummySupervisor() {
        Supervisor dummySupervisor = new Supervisor();
        dummySupervisor.setId(1L);
        dummySupervisor.setLastName("Keys");
        dummySupervisor.setFirstName("Harold");
        dummySupervisor.setEmail("keyh@gmail.com");
        dummySupervisor.setPassword("galaxy29");
        dummySupervisor.setDepartment("Comptabilité");
        dummySupervisor.setMatricule("04736");
        return dummySupervisor;
    }
}
