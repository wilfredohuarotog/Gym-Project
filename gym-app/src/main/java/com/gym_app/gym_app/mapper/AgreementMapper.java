package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.responses.AgreementRenewalResponseDto;
import com.gym_app.gym_app.entities.AgreementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgreementMapper {

    @Mapping(source = "member.firstName", target = "memberName")
    @Mapping(source = "memberShip.name", target = "memberShipName")
    @Mapping(source = "member.status", target = "status")
    AgreementRenewalResponseDto toResponseDto (AgreementEntity agreement);

}
