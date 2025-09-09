package com.gym_app.gym_app.dto;

import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.emuns.MemberShipName;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MemberShipDto(

        @NotBlank
        MemberShipName name,

        @NotNull
        List<Long> classes_id
) {
}
