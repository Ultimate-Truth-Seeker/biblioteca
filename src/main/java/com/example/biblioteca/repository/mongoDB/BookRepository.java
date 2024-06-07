package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, Long> {
}
