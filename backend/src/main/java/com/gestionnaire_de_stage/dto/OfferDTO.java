package com.gestionnaire_de_stage.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OfferDTO {
    @NotBlank(message = "Le departement est vide.")
    @Size(min = 2, message = "Le departement doit avoir au minimum 2 lettres.")
    private String department;

    @NotBlank(message = "Le titre est vide.")
    @Size(min = 2, message = "Le titre doit avoir au minimum 2 lettres.")
    private String title;

    @NotBlank(message = "La description est vide.")
    @Size(min = 2, message = "La description doit avoir au minimum 2 lettres.")
    private String description;

    @Size(min = 2, message = "L'addresse doit avoir au minimum 2 lettres.")
    @NotBlank(message = "L'addresse est vide.")
    private String address;

    @Min(value = 0, message = "Le salaire n'est pas positif.")
    private double salary;

    @Min(value = 1, message = "Le id de l'utilsateur n'est pas positif.")
    private long creator_id;
}