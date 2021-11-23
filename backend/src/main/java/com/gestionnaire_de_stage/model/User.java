package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SequenceGenerator(name = "user_seq", initialValue = 6)
@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {

    @JsonCreator
    public User(
            @JsonProperty("id") Long id,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone,
            @JsonProperty("password") String password
    ) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 32, message = "Le nom doit contenir entre 2 et 32 caractères")
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 32, message = "Le prénom doit contenir entre 2 et 32 caractères")
    private String firstName;

    @NotBlank
    @Email(message = "Le courriel doit être valide")
    private String email;

    private String phone;

    @NotBlank
    @Size(min = 8, max = 64, message = "Le mot de passe doit contenir entre 8 et 64 caractères")
    private String password;
}
