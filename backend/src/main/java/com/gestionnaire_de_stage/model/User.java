package com.gestionnaire_de_stage.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String nom;
    private String prenom;
    private String couriel;
    private String numTel;
    private String mdp;
}
