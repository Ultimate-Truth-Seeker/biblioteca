package com.example.biblioteca.repository.postgreSQL;

import com.example.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepositorysql extends JpaRepository<Loan, Integer> {
    boolean existsByBookId(Integer id);
    boolean existsByUserId(Integer id);
    Optional<Loan> findByBookId(Integer id);
    Optional<Loan> findByUserId(Integer id);
}
