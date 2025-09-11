package com.gym_app.gym_app.dto.responses;

import lombok.Builder;

@Builder
public record RegistrationResponseDto(

        Long id,

        String memberName,

        String className,

        String ScheduleName

) {
}
