package com.gestionnaire_de_stage.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OfferDTO {
    @NotBlank(message = "Le departement ne peut pas être vide.")
    @Size(min = 2, message = "Le departement doit avoir au minimum 2 caractères.")
    @NotNull(message = "Le departement est nécessaire.")
    private String department;

    @NotBlank(message = "Le titre ne peut pas être vide.")
    @Size(min = 2, message = "Le titre doit avoir au minimum 2 caractères.")
    @NotNull(message = "Le titre est nécessaire.")
    private String title;

    @NotBlank(message = "La description ne peut pas être vide.")
    @Size(min = 2, message = "La description doit avoir au minimum 2 caractères.")
    @NotNull(message = "La description est nécessaire.")
    private String description;

    @Size(min = 2, message = "L'addresse doit avoir au minimum 2 caractères.")
    @NotBlank(message = "L'addresse ne peut pas être vide.")
    @NotNull(message = "L'addresse est nécessaire.")
    private String address;

    @Min(value = 0, message = "Le salaire ne peut être négatif.")
    private double salary;

    @Min(value = 1, message = "Le id du créateur ne peut être négatif.")
    private long creator_id;
}