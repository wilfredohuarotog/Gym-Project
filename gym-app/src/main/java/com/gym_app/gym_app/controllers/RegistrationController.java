package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.requests.RegistrationDto;
import com.gym_app.gym_app.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> saveRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        registrationService.saveRegistration(registrationDto);
        //return new ResponseEntity<>("Registration saved",HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration saved");
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
