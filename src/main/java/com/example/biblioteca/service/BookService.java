package com.example.biblioteca.service;

import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book save(Book book);

    Optional<Book> get(Integer id);

    void remove(Integer id);

    List<Book> getAll();

    Optional<Book> findByIsbn(String Isbn);
    Optional<Book> findByTitle(String title);

    Optional<Loan> tryLoan(Integer id, String Authorization);
    boolean tryReserve(Integer id, String Authorization);
}
