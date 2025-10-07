package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.RegistrationDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.repositories.RegistrationRepository;
import com.gym_app.gym_app.repositories.ScheduleRepository;
import com.gym_app.gym_app.validators.RegistrationValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RegistrationValidatorServiceImpl implements RegistrationValidatorService {

    private final MemberRepository memberRepository;
    private final RegistrationRepository registrationRepository;

    @Override
    public void validateNewRegistration(MemberEntity member,
                                        ClassesEntity classes,
                                        ScheduleEntity schedule) {

        if (member.getStatus().equals(MemberStatus.INACTIVE)) {
            throw new BadRequestException("Member is inactive");
        }

        if (!memberRepository.findClassesByMemberDni(member.getDni()).contains(classes)) {
            throw new BadRequestException("You cannot enroll in this class, it is not covered by your membership.");
        }

        if (!classes.getSchedule().contains(schedule)) {
            throw new BadRequestException("Schedule incorrect");
        }

        if (schedule.getDay().equals(LocalDate.now().getDayOfWeek()) &&
                schedule.getEndTime().isBefore(LocalTime.now())) {
            throw new BadRequestException("The class has ended. Registration for the next class begins the following day.");
        }

        if (registrationRepository.existsByMemberIdAndClassesIdAndScheduleId(
                member.getId(), classes.getId(), schedule.getId())) {
            throw new BadRequestException("There is already a record with the same class and schedule for this member");
        }

        if (classes.getCapacity() <= registrationRepository.countByClassesIdAndScheduleId(classes.getId(), schedule.getId())) {
            throw new BadRequestException("The class in this schedule is full. Registration for the next class begins the following day");
        }
    }
}
