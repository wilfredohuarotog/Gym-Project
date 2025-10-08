package com.mysecurity3;

import com.mysecurity3.domain.UserEntity;
import com.mysecurity3.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class Mysecurity3Application {

	public static void main(String[] args) {
		SpringApplication.run(Mysecurity3Application.class, args);

	}

	@Bean
	CommandLineRunner initData(UserEntityRepository userRepository, PasswordEncoder passwordEncoder) {

		return args -> {

			if (userRepository.count() == 0) {

				UserEntity admin = new UserEntity();
				admin.setUsername("wilfredo");
				admin.setPassword(passwordEncoder.encode("1234"));
				admin.setEmail("will@gmail.com");
				admin.setRole("ADMIN");

				UserEntity user = new UserEntity();
				user.setUsername("carlos");
				user.setPassword(passwordEncoder.encode("12345"));
				user.setEmail("carlos@gmail.com");
				user.setRole("USER");

				userRepository.saveAll(List.of(admin, user));

			}
		};
	}

}
