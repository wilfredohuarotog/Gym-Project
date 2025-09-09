package com.gym_app.gym_app.dto;

import com.gym_app.gym_app.entities.emuns.ClassesName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClassesDto(

        @NotBlank
        ClassesName name,

        @NotNull
        List<Long> scheduleId,

        @NotNull
        Long coachId,

        @NotNull
        @Min(value = 10)
        @Max(value = 50)
        Long capacity

) {
}
