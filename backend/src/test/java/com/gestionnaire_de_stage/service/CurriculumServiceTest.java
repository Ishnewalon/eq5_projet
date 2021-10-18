package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Monitor;
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
import java.util.Optional;
import java.util.stream.Stream;

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
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain","some xml".getBytes());
        when(studentService.getOneByID(any())).thenReturn(student);

        Curriculum actual = curriculumService.convertMultipartFileToCurriculum(file, student.getId());

        assertThat(actual.getStudent()).isEqualTo(student);
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullFile() {
        Student student = new Student();

        assertThrows(IllegalArgumentException.class, () -> {
            curriculumService.convertMultipartFileToCurriculum(null, student.getId());
        });
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullStudentID() {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain","some xml".getBytes());

        assertThrows(IllegalArgumentException.class, () -> {
            curriculumService.convertMultipartFileToCurriculum(file, null);
        });
    }

    @Test
    public void testConvertMultipartFileToCurriculum_doesntExistStudentID() throws Exception {
        Student student = new Student();
        student.setId(1L);
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain","some xml".getBytes());
        when(studentService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class, () -> {
            curriculumService.convertMultipartFileToCurriculum(file, student.getId());
        });
    }

    @Test
    public void testCreate_withValidCurriculum() throws Exception {
        Curriculum curriculum = getCurriculum();
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
        Curriculum curriculum = getCurriculum();
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

        assertThrows(IdDoesNotExistException.class, () -> {
            curriculumService.getOneByID(invalidID);
        });
    }

    @Test
    public void testGetAll() {
        when(curriculumRepository.findAll()).thenReturn(getCurriculumList());

        List<Curriculum> actualList = curriculumService.getAll();

        assertThat(actualList.size()).isGreaterThan(0);
    }


    private Curriculum getCurriculum() {
        Student student = new Student();
        student.setId(1L);

        Curriculum curriculum = new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                student
        );

        return curriculum;
    }

    private List<Curriculum> getCurriculumList() {
        Student student = new Student();
        student.setId(1L);

        Curriculum c1 = new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                student
        );
        Curriculum c2 = new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                student
        );
        Curriculum c3 = new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                student
        );

        return Arrays.asList(c1, c2, c3);
    }
}
