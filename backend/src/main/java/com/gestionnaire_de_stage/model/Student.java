package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student extends User {

    @NotNull
    @Size(min = 7, max = 7, message = "La matricule doit être de 7 chiffres")
    private String matricule;

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;

    private String curriculumPath;
}
