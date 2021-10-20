package com.gestionnaire_de_stage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import lombok.Setter;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurriculumStudentDto {
    private Student student;
    private Curriculum curriculum;
}
