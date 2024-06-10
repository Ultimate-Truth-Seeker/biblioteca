package com.example.biblioteca.service;

import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> get(Integer id);

    void remove(Integer id);

    List<Loan> getAll();

    boolean existsByBookId(Integer id);
    boolean existsByUserId(Integer id);
    Optional<Loan> findByBookId(Integer id);
    Optional<Loan> findByUserId(Integer id);


}
