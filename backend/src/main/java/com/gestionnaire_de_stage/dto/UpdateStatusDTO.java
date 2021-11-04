package com.gestionnaire_de_stage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestionnaire_de_stage.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class UpdateStatusDTO {
    private Long idOfferApplied;
    private boolean isAccepted;

}
