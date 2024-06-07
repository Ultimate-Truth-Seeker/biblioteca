package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, Long> {
    boolean existsByBookId(Long id);
    boolean existsByUserId(Long id);
    Optional<Loan> findByBookId(Long id);
    Optional<Loan> findByUserId(Long id);
}
