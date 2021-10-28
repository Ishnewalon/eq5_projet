package com.gestionnaire_de_stage.model;

import com.gestionnaire_de_stage.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

