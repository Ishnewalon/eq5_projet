package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends User {

    @NotNull
    @Size(min = 7, max = 7, message = "La matricule doit être de 7 chiffres")
    @Column(unique = true)
    private String matricule;

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Curriculum principalCurriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supervisor supervisor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
