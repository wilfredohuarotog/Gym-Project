package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.requests.ClassesDto;
import com.gym_app.gym_app.dto.responses.ClassesResponseDto;

import java.util.List;

public interface ClassesService {

    List<ClassesResponseDto> findAllClasses();

    ClassesResponseDto findById(Long id);

    ClassesResponseDto saveClass(ClassesDto classesDto);

    ClassesResponseDto updateClass(ClassesDto classesDto, Long id);

    void deleteClass(Long id);
}
