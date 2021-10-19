package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Curriculum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Student student;

    private boolean isValid;

    public Curriculum() {
    }

    public Curriculum(String name, String type, byte[] data, Student student) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.student = student;
    }
}
