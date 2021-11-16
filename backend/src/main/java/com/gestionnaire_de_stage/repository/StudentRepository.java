package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Student findStudentByEmailAndPassword(String email, String password);

    List<Student> getAllByPrincipalCurriculum_IsValidAndSupervisorNull(Boolean isValid);

    List<Student> findAllByPrincipalCurriculumIsNull();

    List<Student> findAllByPrincipalCurriculum_IsValid(Boolean isValid);

}
