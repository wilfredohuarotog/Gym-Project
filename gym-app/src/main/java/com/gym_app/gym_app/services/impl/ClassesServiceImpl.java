package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.ClassesDto;
import com.gym_app.gym_app.dto.responses.ClassesResponseDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.CoachEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.ClassesMapper;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.CoachRepository;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.services.ClassesService;
import com.gym_app.gym_app.validators.ClassesValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {

    private final ClassesRepository classesRepository;
    private final CoachRepository coachRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassesMapper classesMapper;
    private final ClassesValidatorService classesValidatorService;

    @Override
    @Transactional(readOnly = true)
    public List<ClassesResponseDto> findAllClasses() {
        return classesRepository.findAll().stream()
                .map(classesMapper::toClassesResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClassesResponseDto findById(Long id) {

        ClassesEntity classes = getClassById(id);
        return classesMapper.toClassesResponse(classes);
    }

    @Override
    @Transactional
    public ClassesResponseDto saveClass(ClassesDto classesDto) {

        CoachEntity coach = getCoachById(classesDto.coachId());

        List<ScheduleEntity> schedules = classesValidatorService.validateNewAndUpdateClass(classesDto, null);

        ClassesEntity newClass = createNewClass(classesDto.name(), classesDto.capacity(), schedules,coach);

        ClassesEntity savedClass = classesRepository.save(newClass);

        return classesMapper.toClassesResponse(savedClass);

    }

    @Override
    @Transactional
    public ClassesResponseDto updateClass(ClassesDto classesDto, Long id) {

        ClassesEntity classes = getClassById(id);

        CoachEntity coach = getCoachById(classesDto.coachId());

        List<ScheduleEntity> schedules = classesValidatorService.validateNewAndUpdateClass(classesDto, id);

        ClassesEntity updatesClass =  updateExistingClass(classes, classesDto.capacity(), classesDto.name(), coach,schedules);

        return classesMapper.toClassesResponse(classesRepository.save(updatesClass));
    }

    @Override
    //@Transactional
    public void deleteClass(Long id) {

        ClassesEntity classes = getClassById(id);

        try {
            classesRepository.delete(classes);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Cannot delete class due to existing reference in the database");
        }
    }


    //Metodos auxiliares

    private ClassesEntity getClassById(Long id) {

        return classesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no class with ID: " + id));
    }

    private CoachEntity getCoachById(Long id) {

        return coachRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no coach with this ID"));
    }

    private ClassesEntity createNewClass(String name,
                                        Long capacity,
                                        List<ScheduleEntity> schedules,
                                        CoachEntity coach) {

        return ClassesEntity.builder()
                .name(name)
                .capacity(capacity)
                .schedule(schedules)
                .coach(coach)
                .build();
    }

    private ClassesEntity updateExistingClass(ClassesEntity classes,
                                       Long capacity,
                                       String name,
                                       CoachEntity coach,
                                       List<ScheduleEntity> schedules) {

        classes.setCapacity(capacity);
        classes.setName(name);
        classes.setCoach(coach);
        classes.setSchedule(schedules);

        return classes;
    }
}
