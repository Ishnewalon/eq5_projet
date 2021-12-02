package com.gestionnaire_de_stage.model;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.listener.OfferApplicationListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(OfferApplicationListener.class)
@Getter
@Setter
public class OfferApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private Offer offer;

    @OneToOne
    private Curriculum curriculum;

    private LocalDateTime interviewDate;

    @OneToOne
    private Session session;

}

