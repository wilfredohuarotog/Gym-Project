package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.requests.RegistrationDto;
import com.gym_app.gym_app.dto.responses.RegistrationResponseDto;
import com.gym_app.gym_app.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/gym/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<RegistrationResponseDto>> findAllRegistration() {
         List<RegistrationResponseDto> registrationResponseDtos = registrationService.findAllRegistration();
         return ResponseEntity.ok(registrationResponseDtos);
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> saveRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.saveRegistration(registrationDto));
    }

    @GetMapping("/search")
    public ResponseEntity<?> registrationsMemberByDni(@RequestParam("dni") String dni) {
        return ResponseEntity.ok(registrationService.registrationsByDni(dni));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistrationById(@PathVariable Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
