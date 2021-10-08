package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Embeddable
public class Curriculum {
    private String curriculumPath;
    private boolean validated;
}