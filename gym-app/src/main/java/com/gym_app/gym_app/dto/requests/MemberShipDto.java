package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MemberShipDto(

        @NotBlank
        String name,

        @NotNull
        List<Long> classes_id
) {
}
