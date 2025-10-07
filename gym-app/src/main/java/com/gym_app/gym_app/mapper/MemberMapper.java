package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.RegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "agreement", ignore = true)
    MemberEntity toEntity(MemberDto memberDto);


    @Mapping(expression = "java(memberEntity.getFirstName()+ \" \"+ memberEntity.getLastName())", target = "name")
    @Mapping(source = "agreement.id", target = "agreementId")
    MemberResponseDto toMemberResponseDto(MemberEntity memberEntity);

    @Mapping(expression = "java(memberEntity.getFirstName()+ \" \"+ memberEntity.getLastName())", target = "name")
    @Mapping(expression = "java(memberClasses(memberEntity.getRegistrations()))", target = "classes")
    ActiveMemberResponseDto toActiveMemberDto(MemberEntity memberEntity);

    void updateEntityFromDto(MemberUpdateDto memberUpdateDto, @MappingTarget MemberEntity memberEntity);

    default List<String> memberClasses(List<RegistrationEntity> registration){

        if (registration == null){
            return List.of();
        }

        return registration.stream()
                .map(reg ->
                        reg.getClasses().getName()+"-> "
                                +reg.getSchedule().getDay()
                                +": "+reg.getSchedule().getStartTime()
                                +" - "+reg.getSchedule().getEndTime())
                .collect(Collectors.toList());
    }
}
