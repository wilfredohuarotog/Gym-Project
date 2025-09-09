package com.gym_app.gym_app.dto.responses;

import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.emuns.MemberShipName;
import jakarta.persistence.*;

import java.util.List;

public record MemberShipResponseDto(

        Long id,

        MemberShipName name,

        List<String> nameClasses

) {
}
