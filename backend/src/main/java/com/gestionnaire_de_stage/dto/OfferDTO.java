package com.gestionnaire_de_stage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @NotBlank(message = "Le courriel est vide")
    private String creator_email;

    @NotBlank(message = "La date de debut est vide")
    private LocalDate dateDebut;

    @NotBlank(message = "La date de fin est vide")
    private LocalDate dateFin;

    @NotBlank(message = "Le nombre de semaine est vide")
    private String nbSemaine;

    @NotBlank(message = "L'horaire de travail est vide")
    private String horaireTravail;

    @NotBlank(message = "Le nombre d'heure par semaine est vide")
    private String nbHeureSemaine;

    private Long idSession;
}