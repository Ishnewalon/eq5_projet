package com.gestionnaire_de_stage.service;

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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

        Curriculum actualCurriculum = curriculumService.convertMultipartFileToCurriculum(file, student.getId());

        assertThat(actualCurriculum.getStudent()).isEqualTo(student);
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullFile() {
        Student student = new Student();

        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(null, student.getId()));
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullStudentID() {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(file, null));
    }

    @Test
    public void testConvertMultipartFileToCurriculum_doesntExistStudentID() throws Exception {
        Student student = new Student();
        student.setId(1L);
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        when(studentService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(file, student.getId()));
    }

    @Test
    public void testCreate_withValidCurriculum() throws IdDoesNotExistException {
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumRepository.save(any())).thenReturn(dummyCurriculum);

        Curriculum actualCurriculum = curriculumService.create(dummyCurriculum);

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    public void testCreate_withNullCurriculum() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.create(null));
    }

    @Test
    public void testGetByID_withValidID() throws Exception {
        Long validID = 1L;
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumRepository.existsById(any())).thenReturn(true);
        when(curriculumRepository.getById(any())).thenReturn(dummyCurriculum);

        Curriculum actualCurriculum = curriculumService.getOneByID(validID);

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Long invalidID = 5L;
        when(curriculumRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.getOneByID(invalidID));
    }

    @Test
    public void testGetAll() {
        when(curriculumRepository.findAll()).thenReturn(getDummyCurriculumList());

        List<Curriculum> actualCurriculumList = curriculumService.getAll();

        assertThat(actualCurriculumList.size()).isGreaterThan(0);
    }


    private Curriculum getDummyCurriculum() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);

        return new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                dummyStudent
        );
    }

    private List<Curriculum> getDummyCurriculumList() {
        Curriculum dummyCurriculum1 = getDummyCurriculum();
        Curriculum dummyCurriculum2 = getDummyCurriculum();
        Curriculum dummyCurriculum3 = getDummyCurriculum();

        return Arrays.asList(dummyCurriculum1, dummyCurriculum2, dummyCurriculum3);
    }
}
