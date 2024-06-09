package com.example.biblioteca.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "loans")
@Builder
public class Loan {
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String bookId;
    private String userId;
    private Date loanDate;
    private Date devolutionDate;

    public void update(Loan loan) {
        setBookId(loan.getBookId());
        setLoanDate(loan.getLoanDate());
        setDevolutionDate(loan.getDevolutionDate());
        setUserId(loan.getUserId());
    }
}
