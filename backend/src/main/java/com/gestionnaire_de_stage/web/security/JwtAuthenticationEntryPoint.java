package com.gestionnaire_de_stage.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String message = "Unauthorized(401) => [" + request.getRemoteAddr() + "]:[" + request.getRemotePort() + "] -> " + authException.getLocalizedMessage();

        log.info("session : [" + request.getSession().getId() + "], " + message);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized : " + request.getRemoteAddr() + ":" + request.getRemotePort() + " -> " + authException.getLocalizedMessage());
    }

}
