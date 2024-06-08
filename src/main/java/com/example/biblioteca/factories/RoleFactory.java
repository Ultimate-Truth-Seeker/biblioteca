package com.example.biblioteca.factories;

import com.example.biblioteca.exception.RoleNotFoundException;
import com.example.biblioteca.model.ERole;
import com.example.biblioteca.model.Role;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {


    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "admin" -> {
                return new Role(ERole.ROLE_ADMIN);
            }
            case "user" -> {
                return new Role(ERole.ROLE_USER);
            }
            case "super_admin" -> {
                return new Role(ERole.ROLE_SUPER_ADMIN);
            }
            default -> throw new RoleNotFoundException("No role found for " +  role);
        }
    }

}