package com.example.biblioteca;

import com.example.biblioteca.service.BookService;
import com.example.biblioteca.service.LoanService;
import com.example.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@TestConfiguration
@EnableMongoRepositories(basePackages = "com.example.biblioteca.repository.mongoDB")
@EnableJpaRepositories(basePackages = "com.example.biblioteca.repository.postgreSQL")

public class ServiceConfiguration {
    @Value("${app.repository}")
    private String repository;

    @Autowired
    private ApplicationContext context;

    @Bean
    public BookService bookService() {
        return context.getBean(repository + "book", BookService.class);
    }

    @Bean
    public UserService userService() {
        return context.getBean(repository + "user", UserService.class);
    }

    @Bean
    public LoanService loanService() {
        return context.getBean(repository+"loan", LoanService.class);
    }

}
