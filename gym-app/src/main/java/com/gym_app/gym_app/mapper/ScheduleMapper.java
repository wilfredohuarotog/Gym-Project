package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.ScheduleDto;
import com.gym_app.gym_app.entities.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleEntity toEntity(ScheduleDto scheduleDto);
    ScheduleDto toDto(ScheduleEntity scheduleEntity);
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ScheduleDto scheduleDto, @MappingTarget ScheduleEntity scheduleEntity);
}
