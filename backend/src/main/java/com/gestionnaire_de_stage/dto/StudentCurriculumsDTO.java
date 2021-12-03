package com.gestionnaire_de_stage.dto;

import com.gestionnaire_de_stage.model.Curriculum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentCurriculumsDTO {

    private Curriculum principal;

    private List<Curriculum> curriculumList;

    public StudentCurriculumsDTO(Curriculum principal, List<Curriculum> curriculumDTOS) {
        this.principal = principal;
        this.curriculumList = curriculumDTOS;
    }
}
