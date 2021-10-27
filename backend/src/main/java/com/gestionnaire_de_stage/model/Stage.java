package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Stage {

    @GeneratedValue
    @Id
    Long id;

    private String filePath;

    @Lob
    private byte[] contract;

    @OneToOne
    private Student student;
}
