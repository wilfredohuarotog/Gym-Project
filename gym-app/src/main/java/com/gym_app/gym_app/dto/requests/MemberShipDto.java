package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MemberShipDto(

        @NotBlank
        String name,

        @Size(min = 1, message = "You must provide at least one class")
        List<Long> classesId
) {
}
