package com.mysecurity3.services;

import com.mysecurity3.domain.UserEntity;
import com.mysecurity3.dto.AuthResponse;
import com.mysecurity3.dto.LoginRequest;
import com.mysecurity3.dto.RegisterRequest;
import com.mysecurity3.jwt.JwtService;
import com.mysecurity3.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserEntityRepository userEntityRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse getTokenFromRegister (RegisterRequest registerRequest){

        UserEntity user = UserEntity.builder()
                .age(registerRequest.getAge())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        userEntityRepository.save(user);

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse getTokenFromLogin (LoginRequest loginRequest) {

        UserEntity userLogin = userEntityRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow( () -> new UsernameNotFoundException("Don't exist this username"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), userLogin.getPassword())) {
            throw new BadCredentialsException("Password incorrect!");
        }

        String token = jwtService.getToken(userLogin);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
