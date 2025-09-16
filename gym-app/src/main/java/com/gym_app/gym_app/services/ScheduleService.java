package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {

    List<ScheduleDto> findAllSchedule();

    ScheduleDto findById(Long id);

    ScheduleDto saveSchedule(ScheduleDto scheduleDto);

    ScheduleDto updateSchedule(ScheduleDto scheduleDto, Long id);

    void deleteSchedule(Long id);
}
