package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.CoachDto;
import com.gym_app.gym_app.dto.RegistrationDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.RegistrationResponseDto;
import com.gym_app.gym_app.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> saveRegistration(@RequestBody RegistrationDto registrationDto){
        registrationService.saveRegistration(registrationDto);
        return new ResponseEntity<>("Registration saved",HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> registrationsMember(@RequestParam("dni") String dni){
        return ResponseEntity.ok(registrationService.registrationsByDni(dni));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable Long id){
        registrationService.deleteById(id);
        return ResponseEntity.ok("Registration deleted");
    }
}
