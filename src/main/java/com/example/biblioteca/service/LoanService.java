package com.example.biblioteca.service;

import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> get(Long id);

    void remove(Long id);

    List<Loan> getAll();

    boolean existsByBookId(Long id);
    boolean existsByUserId(Long id);
    Optional<Loan> findByBookId(Long id);
    Optional<Loan> findByUserId(Long id);


}
