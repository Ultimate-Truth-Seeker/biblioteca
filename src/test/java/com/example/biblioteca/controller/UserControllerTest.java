package com.example.biblioteca.controller;


import com.example.biblioteca.controller.crud.UserController;
import com.example.biblioteca.exception.UserNotFoundException;
import com.example.biblioteca.model.ERole;
import com.example.biblioteca.model.Role;
import com.example.biblioteca.model.User;
import com.example.biblioteca.model.dto.UpdateUserDto;
import com.example.biblioteca.security.UserDetailsImpl;
import com.example.biblioteca.security.UserDetailsServiceImpl;
import com.example.biblioteca.security.jwt.JwtUtils;
import com.example.biblioteca.service.AuthService;
import com.example.biblioteca.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

@Import(SecurityTestConfig.class)
@SpringBootTest
public class UserControllerTest {
    final String BASE_URL = "/users/";
    @MockBean
    private UserService usersService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private AuthService authService;

    @Autowired
    private UserController controller;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(controller).build();
        User user = new User("1", "Ada",  "ada@mail.com", "12345678",true, new Role(ERole.ROLE_SUPER_ADMIN), null);
        when(usersService.get("1")).thenReturn(user);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(UserDetailsImpl.build(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, userDetails.getAuthorities());
//        //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testFindByIdExistingUser() throws Exception {
        User user = new User("2", "Ada",  "ada@mail.com", "12345678",true, new Role(ERole.ROLE_SUPER_ADMIN), null);
        when(usersService.get("2")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "2" ).header("Authorization", "Bearer "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("2")))
                .andExpect(jsonPath("$.username", is("Ada")));

        verify(usersService, times(1)).get("2");
    }

    @Test
    public void testFindByIdNotExistingUser() throws Exception {
        String id = "511";
        when(usersService.get(id)).thenReturn(null);


        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"user with ID: " + id + " not found\"", result.getResolvedException().getMessage()));

        verify(usersService, times(1)).get(id);

    }


    @Test
    public void testSaveNewUser() throws Exception {
        User user = new User(null, "Ada", "ada@mail.com", "123456789", true, null, null);

        when(usersService.save(any())).thenReturn(user);

        String json = "{\"id\":\"1\",\"name\":\"Ada\",\"lastName\":\"Lovelace\"}";

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(usersService, times(1)).save(any());
    }

    @Test
    public void testUpdateExistingUser() throws Exception {
        UpdateUserDto userDto = new UpdateUserDto( "Ada", "123456789");
        User user = new User("2", "Ada", "ada@mail.com", "123456789", true, null, null);

        when(usersService.update(user.getId(), userDto)).thenReturn(user);

        String json = "{\"username\":\"Ada\",\"password\":\"123456789\"}";
        mockMvc.perform(put(BASE_URL + "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(usersService, times(1)).get(user.getId());
    }

    @Test
    public void testUpdateNotExistingUser() throws Exception {
        String id = "1";
        when(usersService.get(id)).thenReturn(null);
        String json = "{\"id\":\"1\",\"name\":\"Ada\",\"lastName\":\"Lovelace\"}";
        mockMvc.perform(put(BASE_URL + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"user with ID: " + id + " not found\"", result.getResolvedException().getMessage()));

        verify(usersService, times(0)).save(any());
    }

    @Test
    public void testDeleteExistingUser() throws Exception {
        User user= new User("1","Ada", "ada@mail.com", "123456789", true, null,null);
        when(usersService.get("1")).thenReturn(user);

        String json = "{\"id\":\"1\",\"name\":\"Ada\",\"lastName\":\"Lovelace\"}";
        mockMvc.perform(delete(BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(usersService, times(1)).remove("1");
    }

    @Test
    public void testDeleteNotExistingUser() throws Exception {
        String id = "1";
        when(usersService.get(id)).thenReturn(null);

        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"user with ID: " + id + " not found\"", result.getResolvedException().getMessage()));

        verify(usersService, times(0)).remove(id);
    }


}

