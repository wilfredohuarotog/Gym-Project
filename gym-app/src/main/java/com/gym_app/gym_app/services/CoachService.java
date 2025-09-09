package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.CoachDto;

import java.util.List;

public interface CoachService {

    List<CoachDto> findAllCoach();

    CoachDto findById(Long id);

    void saveCoach(CoachDto coachDto);

    CoachDto updateCoach(CoachDto coachDto, Long id);

    void deleteCoach(Long id);



}
