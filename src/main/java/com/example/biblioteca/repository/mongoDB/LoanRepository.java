package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepository extends MongoRepository<Loan, Long> {
}
