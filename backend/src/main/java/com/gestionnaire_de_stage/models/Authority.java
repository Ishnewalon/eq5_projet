package com.gestionnaire_de_stage.models;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    //TODO: create authorities
    VIEW_OTHERS_CURRICULUM;

    @Override
    public String getAuthority() {
        return name();
    }
}
