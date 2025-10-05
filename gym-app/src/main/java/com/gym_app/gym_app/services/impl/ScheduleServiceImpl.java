package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.ScheduleMapper;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

        List<ScheduleEntity> existingSchedules =
                scheduleRepository.findByDay(scheduleDto.day());

        LocalTime newStart = scheduleDto.startTime();
        LocalTime newEnd = scheduleDto.endTime();
        LocalTime duration = newStart.plusHours(1);

        if (newEnd.isBefore(duration)){
            throw new BadRequestException("The minimum duration for a schedule must be at least 1 hour.");
        }

        boolean overlaps = existingSchedules.stream().anyMatch(s -> {
            LocalTime existingStart = s.getStartTime();
            LocalTime existingEnd = s.getEndTime();

            // Solapamiento o duplicado exacto
            return (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart))
                    || (newStart.equals(existingStart) && newEnd.equals(existingEnd));
        });

        if (overlaps) {
            throw new BadRequestException(
                    "The schedule "+ scheduleDto.day() +" [" + newStart + " - " + newEnd + "] overlaps or duplicates an existing schedule."
            );
        }

        ScheduleEntity schedule = scheduleRepository.save(scheduleMapper.toEntity(scheduleDto));
        return scheduleMapper.toDto(schedule);
    }

    @Override
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto, Long id) {

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Schedule with this ID: "+ id+ "doesn't exist"));

        List<ScheduleEntity> existingSchedules =
                scheduleRepository.findByDayAndIdNot(scheduleDto.day(), id);

        LocalTime newStart = scheduleDto.startTime();
        LocalTime newEnd = scheduleDto.endTime();
        LocalTime duration = newStart.plusHours(1);

        if (newEnd.isBefore(duration)){
            throw new BadRequestException("The minimum duration for a schedule must be at least 1 hour.");
        }

        boolean overlaps = existingSchedules.stream().anyMatch(s -> {
            LocalTime existingStart = s.getStartTime();
            LocalTime existingEnd = s.getEndTime();

            // Solapamiento o duplicado exacto
            return (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart))
                    || (newStart.equals(existingStart) && newEnd.equals(existingEnd));
        });

        if (overlaps) {
            throw new BadRequestException(
                    "The schedule "+scheduleDto.day() +" [" + newStart + " - " + newEnd + "] overlaps or duplicates an existing schedule."
            );
        }

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
