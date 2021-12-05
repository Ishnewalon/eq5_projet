package com.gestionnaire_de_stage.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OfferAppDTO {
    @NotNull(message = "Le id de l'offre ne peut pas être null")
    private Long idOffer;
    @NotNull(message = "Le id de l'étudiant ne peut pas être null")
    private Long idStudent;
}
