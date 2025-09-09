package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.AgreementDto;
import com.gym_app.gym_app.dto.MemberDto;
import com.gym_app.gym_app.entities.MemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberEntity toEntity(MemberDto memberDto);

}
