package com.example.biblioteca.service;

import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> get(String id);

    void remove(String id);

    List<Loan> getAll();

    boolean existsByBookId(String id);
    boolean existsByUserId(String id);
    Optional<Loan> findByBookId(String id);
    Optional<Loan> findByUserId(String id);


}
