package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OfferApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Offer offer;

    @ManyToOne
    private Student student;
}
