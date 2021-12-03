package com.gestionnaire_de_stage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Stage {

    @OneToOne
    Contract contract;
    @Lob
    byte[] evalMilieuStage;
    @Lob
    byte[] evalStagiaire;
    @GeneratedValue
    @Id
    private Long id;
}
