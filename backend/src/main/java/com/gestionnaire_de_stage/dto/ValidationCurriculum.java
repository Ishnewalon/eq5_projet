package com.gestionnaire_de_stage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@ToString(of = {"id", "valid"})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationCurriculum {
    private Long id;
    private boolean valid;
}