package com.example.biblioteca.service;

import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book save(Book book);

    Optional<Book> get(String id);

    void remove(String id);

    List<Book> getAll();

    Optional<Book> findByIsbn(String Isbn);
    Optional<Book> findByTitle(String title);

    Optional<Loan> tryLoan(String id, String Authorization);
    boolean tryReserve(String id, String Authorization);
}
