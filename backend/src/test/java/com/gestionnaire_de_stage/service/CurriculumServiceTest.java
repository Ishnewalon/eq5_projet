package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurriculumServiceTest {

    @InjectMocks
    private CurriculumService curriculumService;

    @Mock
    private CurriculumRepository curriculumRepository;

    @Mock
    private StudentService studentService;

    @Test
    public void testConvertMultipartFileToCurriculum_WithValidData() throws IOException, IdDoesNotExistException {
        Student student = new Student();
        student.setId(1L);
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        when(studentService.getOneByID(any())).thenReturn(student);

        Curriculum actual = curriculumService.convertMultipartFileToCurriculum(file, student.getId());

        assertThat(actual.getStudent()).isEqualTo(student);
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullFile() {
        Student student = new Student();

        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(null, student.getId())
        );
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullStudentID() {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(file, null)
        );
    }

    @Test
    public void testConvertMultipartFileToCurriculum_doesntExistStudentID() throws Exception {
        Student student = new Student();
        student.setId(1L);
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        when(studentService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class, () -> curriculumService.convertMultipartFileToCurriculum(file, student.getId()));
    }

    @Test
    public void testCreate_withValidCurriculum() {
        Curriculum curriculum = getDummyCurriculum();
        when(curriculumRepository.save(any())).thenReturn(curriculum);

        Curriculum actual = curriculumService.create(curriculum);

        assertThat(actual).isEqualTo(curriculum);
    }

    @Test
    public void testCreate_withNullCurriculum() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.create(null));
    }

    @Test
    public void testGetByID_withValidID() throws Exception {
        Long validID = 1L;
        Curriculum curriculum = getDummyCurriculum();
        when(curriculumRepository.existsById(any())).thenReturn(true);
        when(curriculumRepository.getById(any())).thenReturn(curriculum);

        Curriculum actual = curriculumService.getOneByID(validID);

        assertThat(actual).isEqualTo(curriculum);
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Long invalidID = 5L;
        when(curriculumRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> curriculumService.getOneByID(invalidID));
    }

    @Test
    public void testGetAll() {
        List<Student> listOfStudents = List.of(new Student(), new Student(), new Student());
        when(curriculumRepository.findAll()).thenReturn(getDummyCurriculumList(listOfStudents));

        List<Curriculum> actualList = curriculumService.getAll();

        assertThat(actualList.size()).isGreaterThan(0);
    }

    @Test
    public void testFindAllStudentsWithInvalidCurriculum() {
        List<Student> listOfStudents = List.of(new Student(), new Student(), new Student());
        when(curriculumRepository.findAllByIsValidIsNull()).thenReturn(getDummyCurriculumList(listOfStudents));

        List<Student> studentList = curriculumService.findAllStudentsWithCurriculumNotValidatedYet();

        assertThat(studentList).isEqualTo(listOfStudents);
    }

    @Test
    public void testFindAllStudentsWithInvalidCurriculum_withEmptyList() {
        when(curriculumRepository.findAllByIsValidIsNull()).thenReturn(getDummyCurriculumList(Collections.emptyList()));

        List<Student> studentList = curriculumService.findAllStudentsWithCurriculumNotValidatedYet();

        assertThat(studentList).isEqualTo(Collections.emptyList());
    }


    @Test
    void testValidate() throws Exception {
        Curriculum curriculum = getDummyCurriculum();

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));

        assertThat(curriculumService.validate(curriculum.getId(), true)).isTrue();
    }

    @Test
    void testValidate_whenCvNonExistent() {
        Curriculum curriculum = getDummyCurriculum();

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.validate(curriculum.getId(), true));
    }

    @Test
    void testValidate_whenCurriculumAlreadyTreated() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setIsValid(true);
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));

        assertThrows(CurriculumAlreadyTreatedException.class, () ->
                curriculumService.validate(curriculum.getId(), true));
    }

    @Test
    void testValidate_withIdCurriculumNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.validate(null, true));
    }

    @Test
    void testReject() throws Exception {
        Curriculum curriculum = getDummyCurriculum();

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));

        assertThat(curriculumService.validate(curriculum.getId(), false)).isTrue();
    }

    @Test
    void testReject_whenCvNonExistent() {
        Curriculum curriculum = getDummyCurriculum();

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.validate(curriculum.getId(), false));
    }

    @Test
    void testReject_whenCurriculumAlreadyTreated() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setIsValid(true);
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));

        assertThrows(CurriculumAlreadyTreatedException.class, () ->
                curriculumService.validate(curriculum.getId(), false));
    }

    @Test
    void testReject_withIdCurriculumNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.validate(null, false));
    }

    @Test
    void testFindOneById() throws Exception {
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        Curriculum oneById = curriculumService.findOneById(dummyCurriculum.getId());

        assertThat(oneById).isEqualTo(dummyCurriculum);
    }

    @Test
    void testFindOneById_withIdNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.findOneById(null));
    }

    @Test
    void testFindOneById_withCurriculumNonExistent() {
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.findOneById(34L));
    }


    private List<Curriculum> getDummyCurriculumList(List<Student> listOfStudents) {

        List<Curriculum> c = new ArrayList<>();
        for (Student stuuu : listOfStudents) {
            Curriculum dummyCurriculum = getDummyCurriculum();
            dummyCurriculum.setStudent(stuuu);
            c.add(dummyCurriculum);
        }
        return c;
    }

    Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setName("myFileeee");
        curriculum.setType("pdf");
        curriculum.setId(1L);
        return curriculum;
    }
}
