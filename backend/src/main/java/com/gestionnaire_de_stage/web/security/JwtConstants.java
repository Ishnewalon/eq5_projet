package com.gestionnaire_de_stage.web.security;

public interface JwtConstants {
    long EXPIRATION_TIME = 1_800_000;
    String TOKEN_PREFIX = "Bearer ";
    String REQ_HEADER = "Authorization";
}
