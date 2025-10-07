package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.CoachDto;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.repositories.CoachRepository;
import com.gym_app.gym_app.validators.CoachValidatorService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoachValidatorServiceImpl implements CoachValidatorService {

    private final CoachRepository coachRepository;

    @Override
    public void validateNewCoach(CoachDto coachDto) {

        validateUniqueDni(coachDto.dni(), null);
        validateUniquePhoneNumber(coachDto.phoneNumber(), null);

    }

    @Override
    public void validateUpdateCoach(CoachDto coachDto, Long id) {

        validateUniqueDni(coachDto.dni(), id);
        validateUniquePhoneNumber(coachDto.phoneNumber(), id);

    }

    private void validateUniquePhoneNumber(String phoneNumber, Long id) {

        boolean exist = (id == null) ?
                coachRepository.existsByPhoneNumber(phoneNumber) :
                coachRepository.existsByPhoneNumberAndIdNot(phoneNumber, id);

        if (exist) {
            throw new BadRequestException("The phone number already belongs to another coach");
        }

    }

    private void validateUniqueDni(String dni, Long id) {

        boolean exist = (id == null) ?
                coachRepository.existsByDni(dni) :
                coachRepository.existsByDniAndIdNot(dni, id);

        if (exist) {
            throw new BadRequestException("The DNI already belongs to another coach");
        }
    }
}
