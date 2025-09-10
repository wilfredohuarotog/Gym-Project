package com.gym_app.gym_app.dto.responses;

import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.RegistrationEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import jakarta.persistence.*;

import java.util.List;

public record MemberResponseDto(

        Long id,
        String name,
        String dni,
        String email,
        String phoneNumber,
        MemberStatus status,
        Long agreementId
) {
}
