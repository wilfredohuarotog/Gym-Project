package com.gym_app.gym_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CoachDto(

        Long id,
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Pattern(regexp = "^9[0-9]{8}$",
                message = "Please enter a valid phone number")
        String phoneNumber,

        @NotBlank
        @NotBlank
        @Size(min = 8, max = 8, message = "Please enter your DNI correctly")
        String dni
) {
}
