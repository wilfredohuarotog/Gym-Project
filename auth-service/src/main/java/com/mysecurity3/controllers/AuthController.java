package com.mysecurity3.controllers;

import com.mysecurity3.dto.AuthResponse;
import com.mysecurity3.dto.LoginRequest;
import com.mysecurity3.dto.RegisterRequest;
import com.mysecurity3.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping ("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){

        return ResponseEntity.ok(authService.getTokenFromLogin(loginRequest)) ;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest registerRequest){

        return ResponseEntity.ok(authService.getTokenFromRegister(registerRequest));
    }

}
