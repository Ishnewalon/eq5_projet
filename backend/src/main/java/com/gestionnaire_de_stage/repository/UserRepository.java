package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.models.User;
import com.gestionnaire_de_stage.web.security.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username) throws UsernameNotFoundException;

    boolean existsByUsername(String username);

    List<User> findAllByAccountType(AccountType accountType);
}
