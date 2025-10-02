package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;

import java.time.LocalTime;

public record ScheduleDto(

        Long id,

        @NotBlank
        DayOfWeek day,

        @NotNull
        LocalTime startTime,

        @NotNull
        LocalTime endTime

) {
}
