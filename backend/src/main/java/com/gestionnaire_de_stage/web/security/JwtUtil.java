package com.gestionnaire_de_stage.web.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.models.User;
import com.gestionnaire_de_stage.services.JwtUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil implements Serializable {

    @Autowired
    private JwtUserDetailsService jwtService;

    @Value("{jwt.secret}")
    private String secret;

    private static final long serialVersionUID = 1L;

    public UserDetails getUserFromToken(final String token) throws UsernameNotFoundException {
        return jwtService.loadUserByUsername(getUsernameFromToken(token));
    }

    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaimsFromToken(removeJsonBrackets(token)));
    }

    private Claims getClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(removeJsonBrackets(token)).getBody();
    }

    private String removeJsonBrackets(final String token) {
        return token.replace("{", "").replace("}", "");
    }

    private boolean isTokenExpired(final String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String createTokenUser(final User user) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.putAll(mapper.readValue(mapper.writeValueAsString(user), HashMap.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return generateToken(map, user.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date()).setExpiration(getExpirationDateForToken())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8)).compact();
    }

    private Date getExpirationDateForToken() {
        return new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME);
    }


    public boolean validateToken(final String token, final String username) {
        return isUsernameInToken(token, username) && !isTokenExpired(token);
    }

    private boolean isUsernameInToken(final String token, final String username) {
        return getUsernameFromToken(token).equals(username);
    }
}
