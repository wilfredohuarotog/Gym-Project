package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.ClassesDto;
import com.gym_app.gym_app.dto.responses.ClassesResponseDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassesMapper {

    @Mapping(source = "capacity", target = "availableSlots")
//    @Mapping(source = "schedule.day", target = "day")
//    @Mapping(source = "schedule.startTime", target = "startTime")
//    @Mapping(source = "schedule.endTime", target = "endTime")
    @Mapping(expression = "java(scheduleToString(classes.getSchedule()))",target = "schedules")
    @Mapping(expression = "java(classes.getCoach().getFirstName()+ \" \"+ classes.getCoach().getLastName())",target = "coach")
    ClassesResponseDto toClassesResponse(ClassesEntity classes);

    default List<String> scheduleToString (List<ScheduleEntity> schedules){

        if(schedules == null){
            return List.of();
        }
        return schedules.stream()
                .map(s-> "ID: "+s.getId()+"| "
                        +s.getDay()+": "+s.getStartTime()+" "+s.getEndTime())
                .toList();

    }
}
