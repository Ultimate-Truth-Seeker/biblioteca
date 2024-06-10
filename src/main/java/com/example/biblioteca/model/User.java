package com.example.biblioteca.model;

import com.example.biblioteca.model.dto.UpdateUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "users")
@Entity
@Table(name = "users")
@Builder
public class User {
    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;
    private String password;
    private boolean enabled;

    private String Role;
    private Integer reservedBookId;

    public void update(UpdateUserDto updateUserDto) {
        this.username = updateUserDto.getUsername();
        this.password = updateUserDto.getPassword();
    }


}
