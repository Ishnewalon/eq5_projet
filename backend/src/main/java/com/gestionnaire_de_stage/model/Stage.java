package com.gestionnaire_de_stage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Stage {

    @GeneratedValue
    @Id
    private Long id;

    @OneToOne
    Documents documents;
}
