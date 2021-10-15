package com.gestionnaire_de_stage.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class OfferAppDTO {
    @Min(value = 1, message = "Le id de  n'est pas positif.")
    private Long idOffer;
    @Min(value = 1, message = "Le id du curriculum n'est pas positif.")
    private Long idCurriculum;
}
