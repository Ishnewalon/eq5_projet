package com.gestionnaire_de_stage.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString(of = {"id", "lastName", "firstName", "email", "phone", "password"})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 32, message = "Le nom doit contenir entre 2 et 32 charactere")
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 32, message = "Le prénom doit contenir entre 2 et 33 charactere")
    private String firstName;

    @NotBlank
    @Email(message = "Le courriel doit être valide")
    private String email;

    private String phone;

    @NotBlank
    @Size(min = 8, max = 64, message = "Le mot de passe doit contenir entre 8 et 64 caractères")
    private String password;
}
