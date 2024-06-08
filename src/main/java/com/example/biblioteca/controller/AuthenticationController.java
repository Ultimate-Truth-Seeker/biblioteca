package com.example.biblioteca.controller;

import com.example.biblioteca.exception.RoleNotFoundException;
import com.example.biblioteca.exception.UserAlreadyExistsException;
import com.example.biblioteca.model.dto.ApiResponseDto;
import com.example.biblioteca.model.dto.SignInRequestDto;
import com.example.biblioteca.model.dto.SignUpRequestDto;
import com.example.biblioteca.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> signUpUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        return authService.signUpUser(signUpRequestDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseDto<?>> signInUser(@RequestBody @Valid SignInRequestDto signInRequestDto){
        return authService.signInUser(signInRequestDto);
    }
}
