package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Data
@Entity
public class Contract {

    @Id
    Long id;

    private Date managerSignDate;

    private Date monitorSignDate;

    private Date StudentSignDate;

    private String managerSignature;

    private String monitorSignature;

    private String studentSignature;

    @Lob
    private byte[] contractPDF;
}
