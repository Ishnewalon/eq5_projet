package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "curriculums")
public class Curriculum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    private Student student;

    private boolean isValid;

    public Curriculum(){}

    public Curriculum(String name, String type, byte[] data, Student student) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.student = student;
    }
}
