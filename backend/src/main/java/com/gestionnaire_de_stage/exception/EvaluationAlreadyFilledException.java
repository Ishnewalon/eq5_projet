package com.gestionnaire_de_stage.exception;

public class EvaluationAlreadyFilledException extends Exception {
    private final String message;

    public EvaluationAlreadyFilledException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
