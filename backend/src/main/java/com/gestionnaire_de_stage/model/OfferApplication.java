package com.gestionnaire_de_stage.model;

import com.gestionnaire_de_stage.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OfferApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Status status;

    @ManyToOne
    private Offer offer;

    @ManyToOne
    private Curriculum curriculum;
}

