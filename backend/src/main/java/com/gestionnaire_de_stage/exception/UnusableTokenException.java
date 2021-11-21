package com.gestionnaire_de_stage.exception;

import lombok.Getter;

@Getter
public class UnusableTokenException extends Exception {
    private final String message;

    public UnusableTokenException(String message) {
        this.message = message;
    }
}
