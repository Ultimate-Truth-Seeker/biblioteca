package com.example.biblioteca.service;

import com.example.biblioteca.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book save(Book book);

    Optional<Book> get(Long id);

    void remove(Long id);

    List<Book> getAll();

    Optional<Book> findByIsbn(String Isbn);
    Optional<Book> findByTitle(String title);
}
