package com.gestionnaire_de_stage.web.dto;

import com.gestionnaire_de_stage.web.security.AccountType;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SignupRequest implements Serializable {

    @NotBlank
    @Enumerated(EnumType.STRING)
    private final AccountType accountType;

    @NotBlank
    @NotNull
    @Email
    private final String email;

    @Nullable
    private String firstName, lastName;

    @NotBlank
    @NotNull
    private final String username, password;
}