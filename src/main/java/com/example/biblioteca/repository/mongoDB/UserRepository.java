package com.example.biblioteca.repository.mongoDB;

import com.example.biblioteca.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
