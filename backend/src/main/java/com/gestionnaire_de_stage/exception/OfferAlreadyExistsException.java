package com.gestionnaire_de_stage.exception;

public class OfferAlreadyExistsException extends Exception{

    public OfferAlreadyExistsException() {}

    public OfferAlreadyExistsException(String message) {
        super(message);
    }
}