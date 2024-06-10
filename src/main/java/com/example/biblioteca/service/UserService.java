package com.example.biblioteca.service;

import com.example.biblioteca.model.User;
import com.example.biblioteca.model.dto.UpdateUserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);

    User get(Integer id);

    User update(Integer id, UpdateUserDto updateUserDto);

    void remove(Integer id);

    List<User> getAll();

    Optional<User> findByEmail(String email);
}
