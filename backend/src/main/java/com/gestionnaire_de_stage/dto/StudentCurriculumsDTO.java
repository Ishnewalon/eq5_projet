package com.gestionnaire_de_stage.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentCurriculumsDTO {

    CurriculumDTO principal;

    List<CurriculumDTO> curriculumDTOS;

    public StudentCurriculumsDTO(CurriculumDTO principal, List<CurriculumDTO> curriculumDTOS) {
        this.principal = principal;
        this.curriculumDTOS = curriculumDTOS;
    }
}
