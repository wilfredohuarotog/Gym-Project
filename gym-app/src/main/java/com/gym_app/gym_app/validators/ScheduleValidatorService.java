package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleValidatorService {

    void validateSchedule(ScheduleDto scheduleDto, List<ScheduleEntity> existingSchedules);
}
