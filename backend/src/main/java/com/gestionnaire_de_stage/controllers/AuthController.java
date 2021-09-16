package com.gestionnaire_de_stage.controllers;

import com.gestionnaire_de_stage.services.AuthService;
import com.gestionnaire_de_stage.web.dto.LoginRequest;
import com.gestionnaire_de_stage.web.dto.SignupRequest;
import com.gestionnaire_de_stage.web.security.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public JwtToken login(@Validated @RequestBody LoginRequest loginRequest) throws RuntimeException {
        return authService.login(loginRequest);
    }

    @CrossOrigin
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public JwtToken signup(@Validated @RequestBody SignupRequest signupRequest) throws RuntimeException {
        return authService.signup(signupRequest);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/existsByUsername/{username}")
    public boolean existsByUsername(@NotBlank @PathVariable String username) {
        return authService.existsByUsername(username);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/logoff")
    public void logoff() {
        log.debug(SecurityContextHolder.getContext().getAuthentication().toString());
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
