package com.example.biblioteca.service;

import com.example.biblioteca.model.User;
import com.example.biblioteca.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);

    User get(Long id);

    User update(Long id, UserDto userDto);

    void remove(Long id);

    List<User> getAll();
}
