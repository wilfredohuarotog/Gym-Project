package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.responses.RegistrationResponseDto;
import com.gym_app.gym_app.entities.RegistrationEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(
            target = "memberName",
            expression = "java(registration.getMember().getFirstName() + \" \" + registration.getMember().getLastName())"
    )
    @Mapping(
            target = "className",
            expression = "java(registration.getClasses().getName())"
    )
    @Mapping(
            target = "scheduleName",
            expression = "java(formatSchedule(registration.getSchedule()))"
    )
    RegistrationResponseDto toRegistrationResponseDto(RegistrationEntity registration);

    default String formatSchedule(ScheduleEntity schedule) {
        return schedule.getDay().name() + ": " +
                schedule.getStartTime() + " - " + schedule.getEndTime();
    }


}
