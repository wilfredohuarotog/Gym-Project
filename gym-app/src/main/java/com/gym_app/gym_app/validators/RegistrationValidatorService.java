package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.RegistrationDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;

public interface RegistrationValidatorService {

    void validateNewRegistration(MemberEntity member,
                                 ClassesEntity classes,
                                 ScheduleEntity schedule);
}
