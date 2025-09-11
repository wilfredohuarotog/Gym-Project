package com.notification.dto;

public record MemberAgreementDto(

        Long id,
        String name,
        String dni,
        String email,
        String phoneNumber,
        MemberStatus status,
        Long agreementId

){
}
