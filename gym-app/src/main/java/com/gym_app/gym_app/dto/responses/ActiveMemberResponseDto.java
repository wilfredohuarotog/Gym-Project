package com.gym_app.gym_app.dto.responses;

import com.gym_app.gym_app.entities.emuns.MemberStatus;

import java.util.List;

public record ActiveMemberResponseDto(

        String name,
        MemberStatus status,
        List<String> classes

) {
}
