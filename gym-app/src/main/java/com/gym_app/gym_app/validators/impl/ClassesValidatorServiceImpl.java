package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.ClassesDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.validators.ClassesValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClassesValidatorServiceImpl implements ClassesValidatorService {

    private final ClassesRepository classesRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleEntity> validateNewAndUpdateClass(ClassesDto classesDto, Long classId) {

        validateUniqueClassName(classesDto.name(), classId);

        return validateClassSchedule(classesDto, classId);

    }

    private void validateUniqueClassName(String className, Long id) {

        if (classesRepository.existsByNameAndIdNot(className, id)) {
            throw new BadRequestException("There is already a class for that discipline");
        }
    }

    private List<ScheduleEntity> validateClassSchedule(ClassesDto classesDto, Long classId) {

        return classesDto.scheduleId().stream()
                .map(id -> {
                    ScheduleEntity scheduleEntity = getScheduleById(id);
                    //Validando que el horario no este ocupado por otra clase (no puede haber dos o más clases en un mismo horario)
                    validateUniqueSchedule(id, classId);
                    return scheduleEntity;
                })
                .collect(Collectors.toList());
    }

    private ScheduleEntity getScheduleById(Long id) {

        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no schedule with ID: " + id));
    }

    private  void validateUniqueSchedule(Long scheduleId, Long classId) {

        if (classesRepository.existsByScheduleIdAndIdNot(scheduleId, classId)) {
            ClassesEntity classes = classesRepository.findBySchedule_Id(scheduleId)
                    .orElseThrow(() -> new IllegalStateException("Data inconsistency: Schedule conflict detected but conflicting class not found."));
            throw new BadRequestException("The schedule with ID: " + scheduleId + " is already assigned to the class: " + classes.getName());
        }
    }
}
