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
    @Autowired
    private BookRepository bookRepository;

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
}
