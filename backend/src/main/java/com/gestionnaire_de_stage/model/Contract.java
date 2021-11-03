package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Contract {

    @Id
    Long id;

    private LocalDate managerSignDate;

    private LocalDate monitorSignDate;

    private LocalDate StudentSignDate;

    private String managerSignature;

    private String monitorSignature;

    private String studentSignature;

    @OneToOne
    private Student student;

    @Lob
    private byte[] contractPDF;
}
