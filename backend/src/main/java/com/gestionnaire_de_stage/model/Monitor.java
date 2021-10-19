package com.gestionnaire_de_stage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Monitor extends User {

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;
}
