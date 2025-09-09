package com.gym_app.gym_app.dto.responses;

import com.gym_app.gym_app.entities.ClassesEntity;
import jakarta.persistence.*;

import java.util.List;

public record MemberShipResponseDto(

        Long id,

        String name,

        List<String> nameClasses

) {
}
