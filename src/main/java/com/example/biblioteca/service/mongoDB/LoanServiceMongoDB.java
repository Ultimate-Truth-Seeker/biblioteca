package com.example.biblioteca.service.mongoDB;

import com.example.biblioteca.model.Loan;
import com.example.biblioteca.repository.mongoDB.LoanRepository;
import com.example.biblioteca.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoanServiceMongoDB implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> get(String id) {
        return loanRepository.findById(id);
    }

    @Override
    public void remove(String id) {
        loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    public boolean existsByBookId(String id) {
        return loanRepository.existsByBookId(id);
    }

    @Override
    public boolean existsByUserId(String id) {
        return loanRepository.existsByUserId(id);
    }

    @Override
    public Optional<Loan> findByBookId(String id) {
        return loanRepository.findByBookId(id);
    }

    @Override
    public Optional<Loan> findByUserId(String id) {
        return loanRepository.findByUserId(id);
    }
}
