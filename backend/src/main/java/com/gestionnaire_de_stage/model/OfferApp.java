package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data
public class OfferApp {

    @Id
    private Long id;
    @ManyToOne
    private Offer offer;

    @OneToOne
    private Curriculum curriculum;
}
