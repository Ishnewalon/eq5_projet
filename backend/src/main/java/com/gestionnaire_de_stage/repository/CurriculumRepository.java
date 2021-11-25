package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findAllByStudent(Student student);
}
