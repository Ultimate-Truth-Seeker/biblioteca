package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByIsbn(String Isbn);
    Optional<Book> findByTitle(String title);

}
