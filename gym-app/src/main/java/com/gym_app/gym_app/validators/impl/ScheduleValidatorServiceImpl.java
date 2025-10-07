package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.validators.ScheduleValidatorService;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Component
public class ScheduleValidatorServiceImpl implements ScheduleValidatorService {

    public static final int MINIMUM_HOURS_TO_CLASS = 1;
    public static final LocalTime OPENING_TIME= LocalTime.of(6,0);
    public static final LocalTime CLOSING_TIME= LocalTime.of(23,0);

    @Override
    public void validateSchedule(ScheduleDto scheduleDto, List<ScheduleEntity> existingSchedules) {

        validateTimeOrder(scheduleDto.startTime(), scheduleDto.endTime());
        validateOpenHours(scheduleDto.startTime(), scheduleDto.endTime());
        validateNoOverlap(scheduleDto.startTime(), scheduleDto.endTime(), existingSchedules,scheduleDto.day());
        validateMinimumDuration(scheduleDto.startTime(), scheduleDto.endTime());
    }

    private void validateTimeOrder(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new BadRequestException("The start time must be before the end time.");
        }
    }

    private void validateOpenHours(LocalTime startTime, LocalTime endTime) {
        if(startTime.isBefore(OPENING_TIME)||endTime.isAfter(CLOSING_TIME)) {
            throw new BadRequestException("Schedules must be between 6:00 AM and 11:00 PM.");
        }
    }

    private void validateMinimumDuration(LocalTime start, LocalTime end) {

        LocalTime duration = start.plusHours(MINIMUM_HOURS_TO_CLASS);

        if (end.isBefore(duration)){
            throw new BadRequestException("The minimum duration for a schedule must be at least 1 hour.");
        }
    }

    private void validateNoOverlap(LocalTime newStart,
                                  LocalTime newEnd,
                                  List<ScheduleEntity> existingSchedules,
                                  DayOfWeek day) {

        boolean overlaps = existingSchedules.stream().anyMatch(s -> {
            LocalTime existingStart = s.getStartTime();
            LocalTime existingEnd = s.getEndTime();

            // Solapamiento o duplicado exacto
            return (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart))
                    || (newStart.equals(existingStart) && newEnd.equals(existingEnd));
        });

        if (overlaps) {
            throw new BadRequestException(
                    "The schedule "+ day +" [" + newStart + " - " + newEnd + "] overlaps or duplicates an existing schedule."
            );
        }
    }


}
