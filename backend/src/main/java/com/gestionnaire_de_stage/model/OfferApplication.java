package com.gestionnaire_de_stage.model;

import com.gestionnaire_de_stage.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
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
}

