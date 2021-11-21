package com.gestionnaire_de_stage.exception;

public class SessionAlreadyExistException extends Exception {
    private final String message;

    public SessionAlreadyExistException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
