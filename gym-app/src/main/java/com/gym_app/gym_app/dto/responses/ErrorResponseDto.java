package com.gym_app.gym_app.dto.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
public record ErrorResponseDto(

        String error,
        String message,
        int status,
        LocalDateTime timestamp,
        Map<String, List<Object>> details

) {
}
