package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    boolean existsByBookId(String id);
    boolean existsByUserId(String id);
    Optional<Loan> findByBookId(String id);
    Optional<Loan> findByUserId(String id);
}
