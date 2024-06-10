package com.example.biblioteca.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String author;
    private String editorial;
    private String isbn;
    private String reservingUserId;
    private boolean available;

    public void update(Book book) {
        setAuthor(book.getAuthor());
        setAvailable(book.isAvailable());
        setEditorial(book.getEditorial());
        setIsbn(book.getIsbn());
        setTitle(book.getTitle());
    }
}
