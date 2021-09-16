package com.gestionnaire_de_stage.models;

import com.gestionnaire_de_stage.web.dto.SignupRequest;
import com.gestionnaire_de_stage.web.security.AccountType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public  class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    @Column
    private String firstName, lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    private boolean accountNonExpired = true, accountNonLocked = true, credentialsNonExpired = true, enabled = true;

    public static User from(SignupRequest s) {
        User user = new User();
        user.setEmail(s.getEmail());
        user.setPassword(s.getPassword());
        user.setUsername(s.getUsername());
        return user;
    }
}
