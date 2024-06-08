package com.example.biblioteca.repository.postgreSQL;

import com.example.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface LoanRepositorySQL extends JpaRepository<Loan, Long> {
    boolean existsByBookId(Long id);
    boolean existsByUserId(Long id);
    Optional<Loan> findByBookId(Long id);
    Optional<Loan> findByUserId(Long id);
}
