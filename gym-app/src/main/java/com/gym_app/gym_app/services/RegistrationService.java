package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.requests.RegistrationDto;
import com.gym_app.gym_app.dto.responses.RegistrationResponseDto;

import java.util.List;

public interface RegistrationService {

    RegistrationResponseDto saveRegistration(RegistrationDto registrationDto);

    List<RegistrationResponseDto> registrationsByDni(String dni);

    void deleteById(Long id);

}
