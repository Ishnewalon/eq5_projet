package com.gestionnaire_de_stage.model;

import com.gestionnaire_de_stage.listener.ContractListener;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@EntityListeners(ContractListener.class)
public class Contract {//TODO: add fields session

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
    private Monitor monitor;

    @OneToOne
    private Manager manager;

    @Lob
    private byte[] contractPDF;

    @OneToOne
    private Session session;
}
