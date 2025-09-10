package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.MemberDto;
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

    //Recordatorio, no ignorar el agreement pra que se pueda actualizar tbm
    @Mapping(target = "agreement", ignore = true)
    void updateEntityFromDto(MemberDto memberDto, @MappingTarget MemberEntity memberEntity);

    //To activeMember
    default List<String> memberClasses(List<RegistrationEntity> registration){

        if (registration == null){
            return List.of();
        }

        return registration.stream()
                .map(reg ->
                        reg.getClasses().getName()+"-> "
                                +reg.getClasses().getSchedule().stream()
                                .map(sch -> sch.getDay()+": "+sch.getStartTime()+" - "+sch.getEndTime())
                                .collect(Collectors.joining(", "))
                )
                .collect(Collectors.toList());
    }
}
