package com.example.biblioteca.controller.crud;

import com.example.biblioteca.exception.LoanNotFoundException;
import com.example.biblioteca.model.Loan;
import com.example.biblioteca.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans/")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> Loans = loanService.getAll();
        return ResponseEntity.ok(Loans);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan Loan) {
        Loan createdLoan  = loanService.save(Loan);
        URI createdLoanUri = URI.create("/v1/Loans/" + createdLoan.getId());
        return ResponseEntity.created(createdLoanUri).body(createdLoan);
    }



    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Loan> findById(@PathVariable("id") Integer id) {
        Optional<Loan> LoanOptional = loanService.get(id);
        if (LoanOptional.isPresent()) {
            return ResponseEntity.ok(LoanOptional.get());
        } else {
            throw new LoanNotFoundException(id.toString());
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Loan> updateLoan(@PathVariable("id") Integer id, @RequestBody Loan Loan) {
        Optional<Loan> LoanOptional = loanService.get(id);
        if (LoanOptional.isPresent()) {
            LoanOptional.get().update(Loan);
            return ResponseEntity.ok(loanService.save(LoanOptional.get()));
        } else {
            throw new LoanNotFoundException(id.toString());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteLoan(@PathVariable("id") Integer id) {
        Optional<Loan> LoanOptional = loanService.get(id);
        if (LoanOptional.isPresent()) {
            loanService.remove(id);
            return ResponseEntity.ok().build();
        } else {
            throw new LoanNotFoundException(id.toString());
        }
    }

}
