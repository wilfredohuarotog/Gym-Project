package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.ClassesDto;
import com.gym_app.gym_app.entities.ScheduleEntity;

import java.util.List;

public interface ClassesValidatorService {

    List<ScheduleEntity> validateNewAndUpdateClass(ClassesDto classesDto, Long classId);


}
