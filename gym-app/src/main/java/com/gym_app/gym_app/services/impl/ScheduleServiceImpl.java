package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.ScheduleMapper;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleDto> findAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toDto)
                .toList();
    }

    @Override
    public ScheduleDto findById(Long id) {

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Schedule with this ID: "+ id + "doesn't exist"));

        return scheduleMapper.toDto(schedule);
    }

    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {
        ScheduleEntity schedule = scheduleRepository.save(scheduleMapper.toEntity(scheduleDto));
        return scheduleMapper.toDto(schedule);
    }

    @Override
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto, Long id) {

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Schedule with this ID: "+ id+ "doesn't exist"));

        scheduleMapper.updateEntityFromDto(scheduleDto,schedule);

        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Schedule with this ID: "+ id + "doesn't exist"));

        try{
            scheduleRepository.delete(schedule);
        } catch (DataIntegrityViolationException e){
            throw new BadRequestException("Cannot delete schedule due to existing references in the database");
        }
    }
}
