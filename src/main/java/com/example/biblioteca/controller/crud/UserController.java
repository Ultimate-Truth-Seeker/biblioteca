package com.example.biblioteca.controller.crud;

import com.example.biblioteca.exception.UserNotFoundException;
import com.example.biblioteca.model.User;
import com.example.biblioteca.model.dto.UpdateUserDto;
import com.example.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser  = userService.save(user);
        URI createdUserUri = URI.create("/v1/users/" + createdUser.getId());
        return ResponseEntity.created(createdUserUri).body(createdUser);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
        User userOptional = userService.get(id);
        if (userOptional != null) {
            return ResponseEntity.ok(userOptional);
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody UpdateUserDto userdto) {
        User userOptional = userService.update(id, userdto);
        if (userOptional != null) {
            return ResponseEntity.ok(userService.get(id));
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        User userOptional = userService.get(id);
        if (userOptional != null) {
            userService.remove(id);
            return ResponseEntity.ok().build();
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

}
