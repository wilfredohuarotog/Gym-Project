package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClassesDto(

        @NotBlank
        String name,

        @NotNull
        List<Long> scheduleId,

        @NotNull
        Long coachId,

        @NotNull
        @Min(value = 10, message = "the minimum capacity is 10")
        @Max(value = 50, message = "the minimum capacity is 10")
        Long capacity

) {
}
