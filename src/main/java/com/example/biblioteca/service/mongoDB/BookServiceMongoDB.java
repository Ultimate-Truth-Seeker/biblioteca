package com.example.biblioteca.service.mongoDB;

import com.example.biblioteca.exception.UserNotFoundException;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.mongoDB.BookRepository;
import com.example.biblioteca.repository.mongoDB.UserRepository;
import com.example.biblioteca.security.jwt.JwtRequestFilter;
import com.example.biblioteca.security.jwt.JwtUtils;
import com.example.biblioteca.service.BookService;
import com.example.biblioteca.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class BookServiceMongoDB implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${app.loanDuration}")
    private int loanDuration;


    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> get(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public void remove(String id) {
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

    @Override
    public Optional<Loan> tryLoan(String id, String Authorization) {
        if (get(id).isPresent()) {
            if (get(id).get().isAvailable()) {
                if (get(id).get().getReservingUserId() != null) {
                    if (!get(id).get().getReservingUserId().equals(getUserDetails(Authorization).getId())) {
                        return Optional.empty();
                    }
                }
                Book updated = get(id).get();
                updated.setAvailable(false);
                updated.setReservingUserId(null);
                bookRepository.save(updated);
                Loan loan =
                        Loan.builder().bookId(id).userId(getUserDetails(Authorization).getId()).loanDate(new Date())
                        .devolutionDate(new Date(new Date().getTime() + loanDuration)).build();
                loanService.save(loan);
                if (getUserDetails(Authorization).getReservedBookId() != null) {
                    User user = getUserDetails(Authorization);
                    if (user.getReservedBookId().equals(id)) {
                        user.setReservedBookId(null);
                        userRepository.save(user);
                    }
                }
                return Optional.of(loan);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean tryReserve(String id, String Authorization) {
        if (get(id).isPresent()) {
            if (!get(id).get().isAvailable() && get(id).get().getReservingUserId() == null) {
                User user = getUserDetails(Authorization);
                if (user.getReservedBookId() != null) {
                    Book previous = get(user.getReservedBookId()).get();
                    previous.setReservingUserId(null);
                    bookRepository.save(previous);
                }
                user.setReservedBookId(id);
                userRepository.save(user);
                Book book = get(id).get();
                book.setReservingUserId(user.getId());
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }


    private User getUserDetails(String Authorization) {
        if (Authorization != null && jwtUtils.validateJwtToken(Authorization)) {
            String username = jwtUtils.getUserNameFromJwtToken(Authorization);
            return userRepository.findByEmail(username).get();
            }
        return null;
    }

    @Scheduled(fixedRate = 30000)
    public void update() {
        Date date = new Date();
        for (Loan loan : loanService.getAll()) {
            if (loan.getDevolutionDate().before(date)) {
                Book book = bookRepository.findById(loan.getBookId()).get();
                book.setAvailable(true);
                bookRepository.save(book);
                loanService.remove(loan.getId());
            }
        }
    }
}
