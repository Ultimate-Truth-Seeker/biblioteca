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
}
