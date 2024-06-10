package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, Integer> {
    boolean existsByBookId(Integer id);
    boolean existsByUserId(Integer id);
    Optional<Loan> findByBookId(Integer id);
    Optional<Loan> findByUserId(Integer id);
}
