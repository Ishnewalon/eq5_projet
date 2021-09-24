package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepository.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @BeforeAll
    public void insertData() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Scott");
        student1.setFirstName("Jordan");
        student1.setEmail("jscotty@gmail.com");
        student1.setPhone("514-546-2375");
        student1.setPassword("rockrocks");
        student1.setAddress("758 George");
        student1.setCity("LaSalle");
        student1.setDepartment("Informatique");
        student1.setPostalCode("H5N 9F2");
        student1.setMatricule("6473943");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jot");
        student2.setFirstName("Paul");
        student2.setEmail("pertFaul@gmail.com");
        student2.setPhone("514-765-8357");
        student2.setPassword("kald329345");
        student2.setAddress("961 Lifew");
        student2.setCity("Verdun");
        student2.setDepartment("Comptabilite");
        student2.setPostalCode("K1F R5V");
        student2.setMatricule("3728243");

        Student student3 = new Student();
        student3.setId(3L);
        student3.setName("Tremblay");
        student3.setFirstName("Emily");
        student3.setEmail("treme@gmail.com");
        student3.setPhone("514-924-7854");
        student3.setPassword("tough8475");
        student3.setAddress("8542 Schevchenko");
        student3.setCity("LaSalle");
        student3.setDepartment("Science Humaine");
        student3.setPostalCode("H3J 1D8");
        student3.setMatricule("1749305");

        studentRepository.saveAll(Arrays.asList(student1,student2,student3));
    }

    @Test
    public void testFindAll() {
        int actual = studentRepository.findAll().size();

        assertEquals(actual, 3);
    }

    @Test
    public void testCreate_withValidStudent() {
        Student student = new Student();
        student.setName("Candle");
        student.setFirstName("Tea");
        student.setEmail("cant@outlook.com");
        student.setPassword("cantPass");
        student.setDepartment("info");
        student.setMatricule("4673943");

        Optional<Student> actual = studentService.create(student);
        assertTrue(actual.isPresent());
    }

    @Test
    public void testCreate_withNullStudent() {
        Optional<Student> actual = studentService.create(null);

        assertTrue(actual.isEmpty());
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
        student.setName("Candle");
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
        student.setName("Candle");
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
