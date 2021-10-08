package com.gestionnaire_de_stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(of = {"id", "valid"})
public class ValidationCurriculum {
    private final Long id;
    private final boolean valid;
}