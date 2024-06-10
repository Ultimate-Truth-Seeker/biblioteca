package com.example.biblioteca.repository.postgreSQL;

import com.example.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepositorysql extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String Isbn);
    Optional<Book> findByTitle(String title);
}
