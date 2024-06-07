package com.example.biblioteca.model;


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
    @jakarta.persistence.Id
    private Long id;
    private String title;
    private String author;
    private String editorial;
    private String isbn;
    private boolean available;
}
