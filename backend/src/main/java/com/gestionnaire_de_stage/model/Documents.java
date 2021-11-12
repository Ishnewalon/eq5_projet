package com.gestionnaire_de_stage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Getter
@Setter
@Entity
public class Documents {

    @GeneratedValue
    @Id
    private Long id;

    @Lob
    private byte[] evalMilieuStage;

    @Lob
    private byte[] evalStagiaire;
}
