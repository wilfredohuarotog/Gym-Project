package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;

import java.time.LocalTime;

public record ScheduleDto(

        Long id,

        @NotNull(message = "Day of week is required")
        DayOfWeek day,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime

) {
}
