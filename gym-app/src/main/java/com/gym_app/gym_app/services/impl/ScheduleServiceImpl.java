package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.ScheduleMapper;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.services.ScheduleService;
import com.gym_app.gym_app.validators.ScheduleValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleValidatorService scheduleValidatorService;

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDto> findAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toDto)
                .toList();
    }

    @Override
    public ScheduleDto findById(Long id) {

        ScheduleEntity schedule = getScheduleById(id);

        return scheduleMapper.toDto(schedule);
    }

    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {

        List<ScheduleEntity> existingSchedules = getSchedulesByDay(scheduleDto.day());

        /*
        Validación de horarios:
        - Se valida que que no haya solapamiento con horarios existentes.
        - Se valida que el nuevo horario de inicio no sea igual ni despues del termino de clase.
        - Se valida que el horario sea dentro de las 6 am a 11 pm (horario de operación).
        - Se valida que el como minimo un horario tenga 1 hora de duración.
         */
        scheduleValidatorService.validateSchedule(scheduleDto, existingSchedules);

        ScheduleEntity savedSchedule = scheduleRepository.save(scheduleMapper.toEntity(scheduleDto));
        return scheduleMapper.toDto(savedSchedule);
    }

    @Override
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto, Long id) {

        ScheduleEntity schedule = getScheduleById(id);

        List<ScheduleEntity> existingSchedules = getSchedulesByDay(scheduleDto.day());

        scheduleValidatorService.validateSchedule(scheduleDto, existingSchedules);

        scheduleMapper.updateEntityFromDto(scheduleDto,schedule);

        ScheduleEntity updatedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(updatedSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {

        ScheduleEntity schedule = getScheduleById(id);

        try{
            scheduleRepository.delete(schedule);
        } catch (DataIntegrityViolationException e){
            throw new BadRequestException("Cannot delete schedule due to existing references in the database");
        }
    }


    //Metodos auxilires
    private ScheduleEntity getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Schedule with this ID: "+ id + "doesn't exist"));
    }

    private List<ScheduleEntity> getSchedulesByDay(DayOfWeek day) {
        return scheduleRepository.findByDay(day);
    }

}
