package com.gestionnaire_de_stage.exception;

import lombok.Getter;

@Getter
public class DoesNotExistException extends Exception {
    private final String message;

    public DoesNotExistException(String message) {
        this.message = message;
    }
}
