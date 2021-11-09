package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends User {

    @NotNull
    @Size(min = 7, max = 7, message = "La matricule doit être de 7 chiffres")
    private String matricule;

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Curriculum principalCurriculum;

    @ManyToOne
    private Supervisor supervisor;
}
