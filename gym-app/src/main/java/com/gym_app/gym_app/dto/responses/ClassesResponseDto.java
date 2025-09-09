package com.gym_app.gym_app.dto.responses;

import java.util.List;

public record ClassesResponseDto(

        Long id,
        String name,
//        String day,
//        String startTime,
//        String endTime,
        List<String> schedules,
        String coach,
        Long availableSlots

) {
}
