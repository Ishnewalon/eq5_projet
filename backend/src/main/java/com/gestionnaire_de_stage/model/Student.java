package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Student extends User{

    @NotNull
    private String matricule;

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;
}
