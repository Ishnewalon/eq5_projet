package com.gestionnaire_de_stage.model;

import javax.persistence.Entity;

@Entity
public class Monitor extends User {

    private String department;

    private String address;

    private String city;

    private String postalCode;
}
