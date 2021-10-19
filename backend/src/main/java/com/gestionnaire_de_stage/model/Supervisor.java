package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supervisor extends User {

    @NotNull
    @Size(min = 5, max = 5, message = "La matricule doit être de 5 chiffres")
    private String matricule;

    @NotNull
    private String department;
}
