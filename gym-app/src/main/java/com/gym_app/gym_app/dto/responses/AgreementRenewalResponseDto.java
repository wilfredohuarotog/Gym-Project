package com.gym_app.gym_app.dto.responses;

import com.gym_app.gym_app.entities.emuns.MemberStatus;

import java.time.LocalDate;

public record AgreementRenewalResponseDto(

        Long id,

        String memberName,

        String memberShipName,

        MemberStatus status,

        LocalDate startDate,

        LocalDate endDate

) {
}
