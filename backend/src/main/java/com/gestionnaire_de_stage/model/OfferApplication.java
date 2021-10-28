package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class OfferApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Offer offer;

    @ManyToOne
    private Curriculum curriculum;

    private Date entrevueDate;

    private EtatOffre etat;

    enum EtatOffre {
        CANDIDATURE_ACCEPTE,
        CANDIDATURE_REJETE,
        EN_ATTENTE,
        EN_ENTREVUE,
        STAGE_TROUVE
    }
}
