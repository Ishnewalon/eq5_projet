package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer {//TODO : session

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Monitor creator;

    @NotBlank(message = "Le département ne peut pas être vide.")
    @Size(min = 2, message = "Le departement doit avoir au minimum 2 caractères.")
    @NotNull(message = "Le département est nécessaire.")
    @Column(nullable = false)
    private String department;

    @NotBlank(message = "Le titre ne peut pas être vide.")
    @Size(min = 2, message = "Le titre doit avoir au minimum 2 caractères.")
    @NotNull(message = "Le titre est nécessaire.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "La description ne peut pas être vide.")
    @Size(min = 2, message = "La description doit avoir au minimum 2 caractères.")
    @NotNull(message = "La description est nécessaire.")
    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    @Size(min = 2, message = "L'adresse doit avoir au minimum 2 caractères.")
    @NotBlank(message = "L'adresse ne peut pas être vide.")
    @NotNull(message = "L'adresse est nécessaire.")
    @Column(nullable = false)
    private String address;

    @Min(value = 0, message = "Le salaire ne peut être négatif.")
    private double salary;

    private Boolean valid;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String nbSemaine;

    private String horaireTravail;

    private String nbHeureSemaine;

    @Transient
    public String creatorEmail() {
        if (creator == null)
            return null;
        return creator.getEmail();
    }
}
