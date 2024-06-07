package com.example.biblioteca.repository.postgreSQL;

import com.example.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<User, Long> {
}
