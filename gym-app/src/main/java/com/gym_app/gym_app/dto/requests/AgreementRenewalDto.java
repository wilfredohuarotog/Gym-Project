package com.gym_app.gym_app.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AgreementRenewalDto(

        @Min(value = 1, message = "There are no memberships shorter than one month")
        @Max(value = 12, message = "The maximum membership is 12 months")
        Long months,

        @NotNull
        Long memberShipId
){
}
