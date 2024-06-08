package com.example.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class Role {

        @Enumerated(EnumType.STRING)
        private ERole name;


        public Role(ERole name) {
            this.name = name;
        }

    }