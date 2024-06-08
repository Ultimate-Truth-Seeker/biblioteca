package com.example.biblioteca.service.mongoDB;

import com.example.biblioteca.model.Book;
import com.example.biblioteca.repository.mongoDB.BookRepository;
import com.example.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookServiceMongoDB implements BookService {

    private final BookRepository bookRepository;

    public BookServiceMongoDB(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> get(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findByIsbn(String Isbn) {
        return bookRepository.findByIsbn(Isbn);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
