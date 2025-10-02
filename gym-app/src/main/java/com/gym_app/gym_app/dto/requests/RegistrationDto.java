package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrationDto(

        @NotBlank
        String dni,

        @NotNull
        Long classesId,

        @NotNull
        Long scheduleId
) {
}
