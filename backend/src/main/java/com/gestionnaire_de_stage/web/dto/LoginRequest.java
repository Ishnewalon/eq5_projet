package com.gestionnaire_de_stage.web.dto;

import com.gestionnaire_de_stage.web.security.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class LoginRequest implements Serializable {
    @NotBlank
    @Enumerated(EnumType.STRING)
    private final AccountType loginType;

    @NotNull
    @NotBlank
    private final String username, password;

    public static LoginRequest from(SignupRequest signupRequest) {
        return new LoginRequest(signupRequest.getAccountType(), signupRequest.getUsername(), signupRequest.getPassword());
    }
}
