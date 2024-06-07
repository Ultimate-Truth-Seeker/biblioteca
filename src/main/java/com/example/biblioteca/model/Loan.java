package com.example.biblioteca.model;

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
    @Id
    @org.springframework.data.annotation.Id
    private Long id;
    private Long bookId;
    private Long userId;
    private Date loanDate;
    private Date devolutionDate;
}
