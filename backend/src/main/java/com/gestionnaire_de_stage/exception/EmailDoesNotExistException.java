package com.gestionnaire_de_stage.exception;

import lombok.Getter;

@Getter
public class EmailDoesNotExistException extends Exception {
    private final String message;

    public EmailDoesNotExistException() {
        this.message = "L'email n'existe pas";
    }

    public EmailDoesNotExistException(String message) {
        this.message = message;
    }

}
