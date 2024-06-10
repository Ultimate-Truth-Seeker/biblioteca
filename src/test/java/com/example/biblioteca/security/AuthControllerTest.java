package com.example.biblioteca.security;

import com.example.biblioteca.exception.RoleNotFoundException;
import com.example.biblioteca.exception.UserAlreadyExistsException;
import com.example.biblioteca.model.dto.ApiResponseDto;
import com.example.biblioteca.controller.AuthenticationController;
import com.example.biblioteca.model.dto.SignInRequestDto;
import com.example.biblioteca.model.dto.SignUpRequestDto;
import com.example.biblioteca.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class AuthControllerTest {

    final String BASE_URL = "/auth/";
    @MockBean
    private AuthService authService;

    @Autowired
    private AuthenticationController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testSignUp() throws Exception {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUsername("user");signUpRequestDto.setEmail("email@example.com");signUpRequestDto.setPassword("password");
        String json = "{\"username\":\"user\",\"email\":\"email@example.com\", \"password\":\"password\"}";
        when(authService.signUpUser(signUpRequestDto)).thenReturn(ResponseEntity.ok(any()));
        mockMvc.perform(post(BASE_URL + "signup").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());
        Mockito.verify(authService, times(1)).signUpUser(signUpRequestDto);
    }

    @Test
    public void testSignIn() throws Exception {
        SignInRequestDto signInRequestDto = new SignInRequestDto("email@example.com", "password");
        String json = "{\"email\":\"email@example.com\", \"password\":\"password\"}";
        when(authService.signInUser(signInRequestDto)).thenReturn(ResponseEntity.ok(any()));
        mockMvc.perform(post(BASE_URL + "signin").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());
        Mockito.verify(authService, times(1)).signInUser(signInRequestDto);
    }

}
