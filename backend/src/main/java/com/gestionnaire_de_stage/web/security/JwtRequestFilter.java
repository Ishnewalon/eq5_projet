package com.gestionnaire_de_stage.web.security;

import com.gestionnaire_de_stage.models.User;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@Component
@Profile("prod")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {

        if(Stream.of(GestionnaireDeStageSecurity.Production.WHITELISTED_URLS).noneMatch(req.getRequestURI()::contains)) {
            final String requestTokenHeader = req.getHeader(JwtConstants.REQ_HEADER);
            String jwtToken = null;
            User user = null;

            if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
                jwtToken = requestTokenHeader.substring(JwtConstants.TOKEN_PREFIX.length());
                try {
                    user = (User) jwtUtil.getUserFromToken(jwtToken);
                } catch (JwtException e) {
                    logger.warn("JWT exception raised", e);
                } catch (UsernameNotFoundException e) {
                    logger.warn("username not found", e);
                }
            }
            if (requestTokenHeader == null) {
                logger.warn("No JWT sent along the request");
            } else if (!requestTokenHeader.startsWith("Bearer ")) {
                logger.warn("JWT Token does not begin with Bearer String");
            }


            if (SecurityContextHolder.getContext().getAuthentication() == null && user != null && jwtUtil.validateToken(jwtToken, user.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(req, res);
    }
}