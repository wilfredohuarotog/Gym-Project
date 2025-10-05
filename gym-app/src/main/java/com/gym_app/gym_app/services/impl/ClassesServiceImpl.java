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
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {

    private final ClassesRepository classesRepository;
    private final CoachRepository coachRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassesMapper classesMapper;

    @Override
    public List<ClassesResponseDto> findAllClasses() {
        return classesRepository.findAll().stream()
                .map(classesMapper::toClassesResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClassesResponseDto findById(Long id) {

        ClassesEntity classes = classesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no class with ID: " + id));

        return classesMapper.toClassesResponse(classes);
    }

    @Override
    public void saveClass(ClassesDto classesDto) {

        if (classesRepository.existsByName(classesDto.name())) {
            throw new BadRequestException("There is already a class for that discipline");
        }

        CoachEntity coach = coachRepository.findById(classesDto.coachId())
                .orElseThrow(() -> new BadRequestException("There is no coach with this ID"));

        List<ScheduleEntity> schedule = classesDto.scheduleId().stream()
                .map(id -> {
                    ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                            .orElseThrow(() -> new BadRequestException("There is no schedule with ID: " + id));

                    //Validando que el horario no este ocupado por otra clase (no puede haber dos o más clases en un mismo horario)
                    if (classesRepository.existsByScheduleIdAndIdNot(id, null)) {
                        ClassesEntity classes = classesRepository.findBySchedule_Id(id).get();
                        throw new BadRequestException("The schedule with ID: " + id + " is already assigned to the class: " + classes.getName());
                    }
                    return scheduleEntity;
                })
                .collect(Collectors.toList());

        classesRepository.save(ClassesEntity.builder()
                .name(classesDto.name())
                .capacity(classesDto.capacity())
                .schedule(schedule)
                .coach(coach)
                .build());
    }

    @Override
    public ClassesResponseDto updateClass(ClassesDto classesDto, Long id) {

        ClassesEntity classes = classesRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no class with this ID: " + id));

        CoachEntity coach = coachRepository.findById(classesDto.coachId())
                .orElseThrow(() -> new BadRequestException("There is no coach with this ID"));

        List<ScheduleEntity> schedule = classesDto.scheduleId().stream()
                .map(idAux -> {
                    ScheduleEntity scheduleEntity = scheduleRepository.findById(idAux)
                            .orElseThrow(() -> new BadRequestException("There is no schedule with ID: " + idAux));

                    //Validando que el horario no este ocupado por otra clase (no puede haber dos o más clases en un mismo horario)
                    if (classesRepository.existsByScheduleIdAndIdNot(idAux, id)) {
                        ClassesEntity classesAux = classesRepository.findBySchedule_Id(idAux).get();
                        throw new BadRequestException("The schedule with ID: " + idAux + " is already assigned to the class: " + classesAux.getName());
                    }
                    return scheduleEntity;

                })
                .collect(Collectors.toList());

        classes.setCapacity(classesDto.capacity());
        classes.setName(classesDto.name());
        classes.setCoach(coach);
        classes.setSchedule(schedule);

        return classesMapper.toClassesResponse(classesRepository.save(classes));
    }

    @Override
    public void deleteClass(Long id) {
        ClassesEntity classes = classesRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no class with this ID"));

        try {
            classesRepository.delete(classes);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Cannot delete class due to existing reference in the database");
        }

    }
}
