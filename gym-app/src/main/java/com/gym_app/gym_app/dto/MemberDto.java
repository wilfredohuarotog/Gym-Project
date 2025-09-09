package com.gym_app.gym_app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record MemberDto (

        @NotBlank
        @Size(min = 3, max = 30, message = "Please enter your firstname correctly")
        String firstName,

        @NotBlank
        @Size(min = 3, max = 30, message = "Please enter your lastname correctly")
        String lastName,

        @NotBlank
        @Size(min = 8, max = 8, message = "Please enter your DNI correctly")
        String dni,

        @NotBlank
        @Email(message = "Please enter a valid email address. Example: mymail@gmail.com")
        String email,

        @Pattern(regexp = "^9[0-9]{8}$",
                message = "Please enter a valid phone number")
        String phoneNumber,

        @Valid
        AgreementDto agreement
) {


}
