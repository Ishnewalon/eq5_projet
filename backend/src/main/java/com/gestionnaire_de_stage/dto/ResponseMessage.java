package com.gestionnaire_de_stage.dto;

import lombok.Data;

@Data
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(){}
}
