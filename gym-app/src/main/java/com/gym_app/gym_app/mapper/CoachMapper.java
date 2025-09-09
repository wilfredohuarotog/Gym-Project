package com.gym_app.gym_app.mapper;

import com.gym_app.gym_app.dto.CoachDto;
import com.gym_app.gym_app.entities.CoachEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    CoachEntity toEntity(CoachDto coachDto);
    CoachDto toDto(CoachEntity coachEntity);
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CoachDto coachDto, @MappingTarget CoachEntity coachEntity);
}
