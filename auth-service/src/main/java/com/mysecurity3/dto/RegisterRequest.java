package com.mysecurity3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    String username;
    String password;
    Integer age;
    String email;
    String role;
}
