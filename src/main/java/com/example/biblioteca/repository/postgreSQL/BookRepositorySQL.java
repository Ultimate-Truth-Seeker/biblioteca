package com.example.biblioteca.repository.postgreSQL;

import com.example.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BookRepositorySQL extends JpaRepository<Book, Long> {

}
