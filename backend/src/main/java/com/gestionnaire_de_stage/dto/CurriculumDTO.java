package com.gestionnaire_de_stage.dto;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CurriculumDTO {
    @NotBlank(message = "Le prénom est vide.")
    @Size(min = 2, message = "Le prénom doit avoir au minimum 2 lettres.")
    private String firstName;

    @NotBlank(message = "Le nom est vide.")
    @Size(min = 2, message = "Le nom doit avoir au minimum 2 lettres.")
    private String lastName;

    @NotBlank(message = "Le nom du c.v est vide.")
    @Size(min = 2, message = "Le nom du c.v doit avoir au minimum 2 lettres.")
    private String fileName;

    @Lob
    private byte[] file;
}