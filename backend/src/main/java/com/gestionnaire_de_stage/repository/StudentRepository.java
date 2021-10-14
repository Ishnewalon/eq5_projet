package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
    Student findStudentByEmailAndPassword(String email, String password);
}
