package com.example.biblioteca.service.postgreSQL;


import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.postgreSQL.BookRepositorysql;
import com.example.biblioteca.repository.postgreSQL.UserRepositorysql;
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

@Component("sqlbook")
public class BookServicePostgreSQL implements BookService {

    @Autowired
    private BookRepositorysql bookRepositorysql;

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserRepositorysql userRepositorysql;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${app.loanDuration}")
    private int loanDuration;


    @Override
    public Book save(Book book) {
        return bookRepositorysql.save(book);
    }

    @Override
    public Optional<Book> get(Integer id) {
        return bookRepositorysql.findById(id);
    }

    @Override
    public void remove(Integer id) {
        bookRepositorysql.deleteById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepositorysql.findAll();
    }

    @Override
    public Optional<Book> findByIsbn(String Isbn) {
        return bookRepositorysql.findByIsbn(Isbn);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepositorysql.findByTitle(title);
    }

    @Override
    public Optional<Loan> tryLoan(Integer id, String Authorization) {
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
                bookRepositorysql.save(updated);
                Loan loan =
                        Loan.builder().bookId(id).userId(getUserDetails(Authorization).getId()).loanDate(new Date())
                                .devolutionDate(new Date(new Date().getTime() + loanDuration)).build();
                loanService.save(loan);
                if (getUserDetails(Authorization).getReservedBookId() != null) {
                    User user = getUserDetails(Authorization);
                    if (user.getReservedBookId().equals(id)) {
                        user.setReservedBookId(null);
                        userRepositorysql.save(user);
                    }
                }
                return Optional.of(loan);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean tryReserve(Integer id, String Authorization) {
        if (get(id).isPresent()) {
            if (!get(id).get().isAvailable() && get(id).get().getReservingUserId() == null) {
                User user = getUserDetails(Authorization);
                if (user.getReservedBookId() != null) {
                    Book previous = get(user.getReservedBookId()).get();
                    previous.setReservingUserId(null);
                    bookRepositorysql.save(previous);
                }
                user.setReservedBookId(id);
                userRepositorysql.save(user);
                Book book = get(id).get();
                book.setReservingUserId(user.getId());
                bookRepositorysql.save(book);
                return true;
            }
        }
        return false;
    }


    private User getUserDetails(String Authorization) {
        if (Authorization != null && jwtUtils.validateJwtToken(Authorization)) {
            String username = jwtUtils.getUserNameFromJwtToken(Authorization);
            return userRepositorysql.findByEmail(username).get();
        }
        return null;
    }

    @Scheduled(fixedRate = 30000)
    public void update() {
        Date date = new Date();
        for (Loan loan : loanService.getAll()) {
            if (loan.getDevolutionDate().before(date)) {
                Book book = bookRepositorysql.findById(loan.getBookId()).get();
                book.setAvailable(true);
                bookRepositorysql.save(book);
                loanService.remove(loan.getId());
            }
        }
    }
}
