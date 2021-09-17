package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Student extends User{

    private String department;

    private String address;

    private String city;

    private String postalCode;
}
