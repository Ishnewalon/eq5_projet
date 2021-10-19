package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
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

    private Boolean isValid;

    public Curriculum() {
    }

    public Curriculum(String name, String type, byte[] data, Student student) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.student = student;
        this.isValid = null;
    }

    public Boolean getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
