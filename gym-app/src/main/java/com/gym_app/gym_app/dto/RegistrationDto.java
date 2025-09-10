package com.gym_app.gym_app.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationDto(

        @NotNull
        Long memberId,

        @NotNull
        Long classId,

        @NotNull
        Long scheduleId
) {
}
