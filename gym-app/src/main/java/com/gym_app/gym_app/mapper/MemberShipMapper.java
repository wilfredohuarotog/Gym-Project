package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.responses.MemberShipResponseDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberShipMapper {

    @Mapping(expression = "java(classesNameToString(memberShip.getClasses()))",target = "nameClasses")
    MemberShipResponseDto toMemberShipResponseDto(MemberShipEntity memberShip);


    default List<String> classesNameToString (List<ClassesEntity> classesEntities){

        if(classesEntities == null){
            return List.of();
        }

        return classesEntities.stream()
                .map(c->c.getName().name())
                .toList();

    }
}
