package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.responses.RegistrationResponseDto;
import com.gym_app.gym_app.entities.RegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {


    //ELIMINAR
    @Mapping(expression = "java(registration.getMember().getFirstName()" +
            "+\" \"+ registration.getMember().getLastName())",target = "memberName")
    //@Mapping(expression = "java(registration.getClasses.getName())", target = "className")
    @Mapping(source = "classes.name", target = "className")
    RegistrationResponseDto toRegistrationResponseDto(RegistrationEntity registration);

}
