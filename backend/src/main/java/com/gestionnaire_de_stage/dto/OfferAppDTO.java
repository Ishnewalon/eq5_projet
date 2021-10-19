package com.gestionnaire_de_stage.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OfferAppDTO {
    @Min(value = 1, message = "Erreur: Le id de l'offre  n'est pas positif.")
    @NotNull(message = "Erreur: Le id de l'offre ne peut pas être null")
    private Long idOffer;
    @Min(value = 1, message = "Erreur: Le id du curriculum n'est pas positif.")
    @NotNull(message = "Erreur: Le id du curriculum ne peut pas être null")
    private Long idCurriculum;
}
