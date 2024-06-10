package com.example.biblioteca.service.postgreSQL;

import com.example.biblioteca.model.Loan;
import com.example.biblioteca.repository.mongoDB.LoanRepository;
import com.example.biblioteca.repository.postgreSQL.LoanRepositorysql;
import com.example.biblioteca.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("sqlloan")
public class LoanServicePostgreSQL implements LoanService {
    @Autowired
    private LoanRepositorysql loanRepository;

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> get(Integer id) {
        return loanRepository.findById(id);
    }

    @Override
    public void remove(Integer id) {
        loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    public boolean existsByBookId(Integer id) {
        return loanRepository.existsByBookId(id);
    }

    @Override
    public boolean existsByUserId(Integer id) {
        return loanRepository.existsByUserId(id);
    }

    @Override
    public Optional<Loan> findByBookId(Integer id) {
        return loanRepository.findByBookId(id);
    }

    @Override
    public Optional<Loan> findByUserId(Integer id) {
        return loanRepository.findByUserId(id);
    }
}
