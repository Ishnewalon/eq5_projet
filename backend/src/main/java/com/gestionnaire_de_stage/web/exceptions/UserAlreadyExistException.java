package com.gestionnaire_de_stage.web.exceptions;

import com.gestionnaire_de_stage.models.User;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String s){
        super(s);
    }

}
