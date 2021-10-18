package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class OfferApplication {

    @Id
    private Long id;
    @ManyToOne
    private Offer offer;

    @ManyToOne
    private Curriculum curriculum;
}
