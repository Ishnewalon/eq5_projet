package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull(message = "creator cannot be null")
    private User creator;

    @NotBlank(message = "Le departement ne peut pas être vide.")
    @Size(min = 2, message = "Le departement doit avoir au minimum 2 caractères.")
    @NotNull(message = "Le departement est nécessaire.")
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

    @Size(min = 2, message = "L'addresse doit avoir au minimum 2 caractères.")
    @NotBlank(message = "L'addresse ne peut pas être vide.")
    @NotNull(message = "L'addresse est nécessaire.")
    @Column(nullable = false)
    private String address;

    @Min(value = 0, message = "Le salaire ne peut être négatif.")
    private double salary;

}
