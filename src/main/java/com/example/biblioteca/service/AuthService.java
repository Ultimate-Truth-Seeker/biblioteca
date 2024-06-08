package com.example.biblioteca.service;

import com.example.biblioteca.exception.RoleNotFoundException;
import com.example.biblioteca.exception.UserAlreadyExistsException;
import com.example.biblioteca.model.dto.ApiResponseDto;
import com.example.biblioteca.model.dto.SignInRequestDto;
import com.example.biblioteca.model.dto.SignUpRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;

    ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto);
}
