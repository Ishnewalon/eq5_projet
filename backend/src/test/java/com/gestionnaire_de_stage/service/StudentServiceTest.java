package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceTest {

    @Autowired
    StudentRepository repository;

    @BeforeAll
    public void insertData(){
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Scott");
        student1.setFirstName("Jordan");
        student1.setEmail("jscotty@gmail.com");
        student1.setNumTel("514-546-2375");
        student1.setPassword("rockrocks");
        student1.setAddress("758 George");
        student1.setCity("LaSalle");
        student1.setDepartment("Informatique");
        student1.setPostalCode("H5N 9F2");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jot");
        student2.setFirstName("Paul");
        student2.setEmail("pertFaul@gmail.com");
        student2.setNumTel("514-765-8357");
        student2.setPassword("kald32");
        student2.setAddress("961 Lifew");
        student2.setCity("Verdun");
        student2.setDepartment("Comptabilite");
        student2.setPostalCode("K1F R5V");

        Student student3 = new Student();
        student3.setId(3L);
        student3.setName("Tremblay");
        student3.setFirstName("Emily");
        student3.setEmail("treme@gmail.com");
        student3.setNumTel("514-924-7854");
        student3.setPassword("tough");
        student3.setAddress("8542 Schevchenko");
        student3.setCity("LaSalle");
        student3.setDepartment("Science Humaine");
        student3.setPostalCode("H3J 1D8");

        repository.saveAll(Arrays.asList(student1, student2, student3));
    }


}
