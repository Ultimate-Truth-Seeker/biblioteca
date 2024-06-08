package com.example.biblioteca.controller.crud;

import com.example.biblioteca.exception.BookNotFoundException;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books/")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllbooks() {
        List<Book> books = bookService.getAll();
        return ResponseEntity.ok(books);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Book> createbook(@RequestBody Book book) {
        Book createdbook  = bookService.save(book);
        URI createdbookUri = URI.create("/v1/books/" + createdbook.getId());
        return ResponseEntity.created(createdbookUri).body(createdbook);
    }

    

    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Long id) {
        Optional<Book> bookOptional = bookService.get(id);
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        } else {
            throw new BookNotFoundException(id.toString());
        }
    }

    @GetMapping("byTitle")
    public  ResponseEntity<Book> findByTitle(@RequestParam String title) {
        Optional<Book> bookOptional = bookService.findByTitle(title);
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        } else {
            throw new BookNotFoundException(title);
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Book> updatebook(@PathVariable("id") Long id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookService.get(id);
        if (bookOptional.isPresent()) {
            bookOptional.get().update(book);
            return ResponseEntity.ok(bookService.get(id).get());
        } else {
            throw new BookNotFoundException(id.toString());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deletebook(@PathVariable("id") Long id) {
        Optional<Book> bookOptional = bookService.get(id);
        if (bookOptional.isPresent()) {
            bookService.remove(id);
            return ResponseEntity.ok().build();
        } else {
            throw new BookNotFoundException(id.toString());
        }
    }
    
}
