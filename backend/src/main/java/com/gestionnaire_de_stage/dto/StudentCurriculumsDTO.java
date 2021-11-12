package com.gestionnaire_de_stage.dto;

import com.gestionnaire_de_stage.model.Curriculum;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class StudentCurriculumsDTO {

    Optional<Curriculum> principal;

    List<Curriculum> curriculumList;

    public StudentCurriculumsDTO(Optional<Curriculum> principal, List<Curriculum> curriculumDTOS) {
        this.principal = principal;
        this.curriculumList = curriculumDTOS;
    }
}
