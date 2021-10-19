package com.gestionnaire_de_stage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Student extends User {

    @NotNull
    @Size(min = 7, max = 7, message = "La matricule doit être de 7 chiffres")
    private String matricule;

    @NotNull
    private String department;

    private String address;

    private String city;

    private String postalCode;

<<<<<<< HEAD
    private String curriculumPath;
=======
    private boolean curriculumValidated;

    @OneToOne
    private Curriculum principalCurriculum;
>>>>>>> 659944d6e1a01196f3da4b8fe5e36229e75ded28
}
