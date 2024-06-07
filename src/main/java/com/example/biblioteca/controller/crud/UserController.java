package com.example.biblioteca.controller.crud;

import com.example.biblioteca.exception.UserNotFoundException;
import com.example.biblioteca.model.User;
import com.example.biblioteca.model.UserDto;
import com.example.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/")
//@PreAuthorize("hasRole(Admin)")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser  = userService.save(user);
        URI createdUserUri = URI.create("/v1/users/" + createdUser.getId());
        return ResponseEntity.created(createdUserUri).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        User userOptional = userService.get(id);
        if (userOptional != null) {
            return ResponseEntity.ok(userOptional);
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userdto) {
        User userOptional = userService.update(id, userdto);
        if (userOptional != null) {
            return ResponseEntity.ok(userService.get(id));
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User userOptional = userService.get(id);
        if (userOptional != null) {
            userService.remove(id);
            return ResponseEntity.ok().build();
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

}
