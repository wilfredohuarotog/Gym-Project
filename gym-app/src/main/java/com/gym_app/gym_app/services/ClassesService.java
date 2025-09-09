package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.ClassesDto;
import com.gym_app.gym_app.dto.responses.ClassesResponseDto;

import java.util.List;

public interface ClassesService {

    List<ClassesResponseDto> findAllClasses();

    ClassesResponseDto findById(Long id);

    void saveClass(ClassesDto classesDto);

    ClassesResponseDto updateClass(ClassesDto classesDto, Long id);

    void deleteCoach(Long id);
}
