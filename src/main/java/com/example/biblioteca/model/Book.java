package com.example.biblioteca.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "books")
@Builder
public class Book {
    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String editorial;
    private String isbn;
    private Integer reservingUserId;
    private boolean available;

    public void update(Book book) {
        setAuthor(book.getAuthor());
        setAvailable(book.isAvailable());
        setEditorial(book.getEditorial());
        setIsbn(book.getIsbn());
        setTitle(book.getTitle());
    }
}
