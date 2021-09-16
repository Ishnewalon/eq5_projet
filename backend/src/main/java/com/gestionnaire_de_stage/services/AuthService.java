package com.gestionnaire_de_stage.services;

import com.gestionnaire_de_stage.models.User;
import com.gestionnaire_de_stage.repository.UserRepository;
import com.gestionnaire_de_stage.web.dto.LoginRequest;
import com.gestionnaire_de_stage.web.dto.SignupRequest;
import com.gestionnaire_de_stage.web.exceptions.UserAlreadyExistException;
import com.gestionnaire_de_stage.web.security.JwtToken;
import com.gestionnaire_de_stage.web.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtToken login(LoginRequest loginRequest) throws RuntimeException{
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = (User) auth.getPrincipal();

        return new JwtToken(jwtUtil.createTokenUser(user));
    }


    public JwtToken signup(SignupRequest signupRequest) throws RuntimeException{

        LoginRequest loginRequest = null;

        if(existsByUsername(signupRequest.getUsername()))
            throw new UserAlreadyExistException(String.format("username '%s' already exists!", signupRequest.getUsername()));

        User user = User.from(signupRequest);

        userRepository.save(user);

       loginRequest = LoginRequest.from(signupRequest);

        return login(loginRequest);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
