package com.example.biblioteca.controller.crud;

import com.example.biblioteca.exception.BookNotFoundException;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import com.example.biblioteca.model.dto.ApiResponseDto;
import com.example.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.header.Header;
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
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> createbook(@RequestBody Book book) {
        Book createdbook  = bookService.save(book);
        URI createdbookUri = URI.create("/v1/books/" + createdbook.getId());
        return ResponseEntity.created(createdbookUri).body(createdbook);
    }

    

    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") String id) {
        Optional<Book> bookOptional = bookService.get(id);
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        } else {
            throw new BookNotFoundException(id.toString());
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<ApiResponseDto<Object>> attemptLoan(@PathVariable String id, @RequestHeader String Authorization) throws Exception {

        Optional<Loan> success =  bookService.tryLoan(id, Authorization.substring(7));
        if (success.isPresent()) {
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Loan created!")
                            .response(success.get())
                            .build()
            );
        }
        throw new Exception();
    }

    @PutMapping("r/{id}")
    public ResponseEntity<ApiResponseDto<Object>> attemptReserve(@PathVariable String id, @RequestHeader String Authorization) throws Exception {

        boolean success = bookService.tryReserve(id, Authorization.substring(7));
        if (success) {
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Book Reserved!")
                            .response(null)
                            .build()
            );
        }
        throw new Exception();
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
    public ResponseEntity<Book> updatebook(@PathVariable("id") String id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookService.get(id);
        if (bookOptional.isPresent()) {
            bookOptional.get().update(book);
            return ResponseEntity.ok(bookService.save(bookOptional.get()));
        } else {
            throw new BookNotFoundException(id.toString());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletebook(@PathVariable("id") String id) {
        Optional<Book> bookOptional = bookService.get(id);
        if (bookOptional.isPresent()) {
            bookService.remove(id);
            return ResponseEntity.ok().build();
        } else {
            throw new BookNotFoundException(id.toString());
        }
    }
    
}
