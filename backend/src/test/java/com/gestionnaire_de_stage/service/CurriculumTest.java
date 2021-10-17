package com.gestionnaire_de_stage.service;

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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurriculumTest {

    @InjectMocks
    private CurriculumService curriculumService;

    @Mock
    private CurriculumRepository curriculumRepository;

    @Mock
    private StudentService studentService;

    @Test
    public void testConvertMultipartFileToCurriculum_WithValidData() throws IOException, Exception {
        Student student = new Student();
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        // when(studentService.getOneByID(any())).thenReturn(Optional.of(student));

        Optional<Curriculum> actual = curriculumService.convertMultipartFileToCurriculum(file, student.getId());

        assertThat(actual).isNotNull();
    }

    @Test
    public void testConvertMultipartFileToCurriculum_WithInvalidData() throws IOException, Exception {
        Student student = new Student();
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        //  when(studentService.getOneByID(any())).thenReturn(Optional.empty());

        Optional<Curriculum> actual = curriculumService.convertMultipartFileToCurriculum(file, student.getId());

        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void testFindAllStudentsWithInvalidCurriculum() {
        List<Student> listOfStudents = List.of(new Student(),new Student(),new Student());
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

    private List<Curriculum> getDummyCurriculumList(List<Student> listOfStudents) {

        List<Curriculum> c = new ArrayList<>();
        for (Student stuuu : listOfStudents) {
            Curriculum curriculum = new Curriculum();
            curriculum.setStudent(stuuu);
            curriculum.setName("myFileeee");
            curriculum.setType("pdf");
            c.add(curriculum);
        }
        return c;
    }

}
