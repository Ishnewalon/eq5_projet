package com.gestionnaire_de_stage.web.security;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

public class MonitorLoginRequest {
    @NotBlank
    @Enumerated(EnumType.STRING)
    private AccountType loginType;
}
