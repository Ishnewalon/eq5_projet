package com.gestionnaire_de_stage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class UpdateStatusDTO {
    private Long idOfferApplied;
    private Boolean isAccepted;

}
