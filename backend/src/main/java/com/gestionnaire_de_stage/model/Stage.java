package com.gestionnaire_de_stage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Stage {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Contract contract;

    @Lob
    private byte[] evalMilieuStage;

    @Lob
    private byte[] evalStagiaire;
}
