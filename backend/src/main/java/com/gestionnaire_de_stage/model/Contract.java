package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate managerSignDate;

    private LocalDate monitorSignDate;

    private LocalDate studentSignDate;

    private String managerSignature;

    private String monitorSignature;

    private String studentSignature;

    @OneToOne
    private Student student;

    @OneToOne
    private Offer offer;

    @OneToOne
    private Manager manager;

    @Lob
    private byte[] contractPDF;
}
