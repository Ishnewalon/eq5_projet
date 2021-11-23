package com.gestionnaire_de_stage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordResetTokenDto {
    private String token;
    private String password;
}
