package com.example.biblioteca.service.mongoDB;

import com.example.biblioteca.model.User;
import com.example.biblioteca.model.dto.UpdateUserDto;
import com.example.biblioteca.repository.mongoDB.UserRepository;
import com.example.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceMongoDB implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(Long id, UpdateUserDto updateUserDto) {
        if (userRepository.existsById(id)) {
            User updated = userRepository.findById(id).get();
            updateUserDto.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            updated.update(updateUserDto);
            return userRepository.save(updated);
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
