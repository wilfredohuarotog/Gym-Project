package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.mapper.ScheduleMapper;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.services.ScheduleService;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(()->new RuntimeException("MMMMM"));

        return scheduleMapper.toDto(schedule);
    }

    @Override
    public void saveSchedule(ScheduleDto scheduleDto) {
        scheduleRepository.save(scheduleMapper.toEntity(scheduleDto));
    }

    @Override
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto, Long id) {

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("hfhfhfh"));

        scheduleMapper.updateEntityFromDto(scheduleDto,schedule);

        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("rurur"));

        scheduleRepository.delete(schedule);

    }
}
