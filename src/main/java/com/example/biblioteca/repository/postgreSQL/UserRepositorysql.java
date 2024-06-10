package com.example.biblioteca.repository.postgreSQL;

import com.example.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositorysql extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
