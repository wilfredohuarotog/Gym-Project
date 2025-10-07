package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.CoachDto;

public interface CoachValidatorService {

    void validateNewCoach(CoachDto coachDto);
    void validateUpdateCoach(CoachDto coachDto, Long id);
}
