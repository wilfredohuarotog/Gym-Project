package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.RegistrationDto;
import com.gym_app.gym_app.dto.responses.RegistrationResponseDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.RegistrationEntity;
import com.gym_app.gym_app.entities.ScheduleEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.RegistrationMapper;
import com.gym_app.gym_app.repositories.*;
import com.gym_app.gym_app.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final MemberRepository memberRepository;
    private final ClassesRepository classesRepository;
    private final ScheduleRepository scheduleRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    @Transactional
    public RegistrationResponseDto saveRegistration(RegistrationDto registrationDto) {

        MemberEntity member = memberRepository.findByDni(registrationDto.dni())
                .orElseThrow(() -> new ResourceNotFoundException("Member with this DNI doesn't exist"));

        if (memberRepository.findStatusByDni(registrationDto.dni()).equals(MemberStatus.INACTIVE)){
            throw new BadRequestException("Member is inactive");
        }

        ClassesEntity classes = classesRepository.findById(registrationDto.classesId())
                .orElseThrow(() -> new ResourceNotFoundException("Class with this ID  doesn't exist"));

        ScheduleEntity schedule = scheduleRepository.findById(registrationDto.scheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not Found"));

        if (!classes.getSchedule().contains(schedule)) {
            throw new BadRequestException("Schedule incorrect");
        }

        if(schedule.getDay().equals(LocalDate.now().getDayOfWeek())&&schedule.getEndTime().isBefore(LocalTime.now())){
            throw new BadRequestException("The class has ended. Registration for the next class begins the following day.");
        }

        if (registrationRepository.existsByMemberIdAndClassesIdAndScheduleId(member.getId(), classes.getId(), schedule.getId())){
            throw new BadRequestException("There is already a record with the same class and schedule for this member");
        }

        if(classes.getCapacity()<=registrationRepository
                .countByClassesIdAndScheduleId(classes.getId(),schedule.getId())){
            throw new ResourceNotFoundException("The class in this schedule is full. Registration for the next class begins the following day");
        }

        RegistrationEntity registration = RegistrationEntity.builder()
                .classes(classes)
                .schedule(schedule)
                .member(member)
                .build();

        registrationRepository.save(registration);

        return RegistrationResponseDto.builder()
                .id(registration.getId())
                .className(registration.getClasses().getName())
                .memberName(registration.getMember().getFirstName()+ " "+registration.getMember().getLastName())
                .ScheduleName(registration.getSchedule().getDay().name()+": "+
                        registration.getSchedule().getStartTime()+" - "+
                        registration.getSchedule().getEndTime())
                .build();
    }

    @Override
    public List<RegistrationResponseDto> registrationsByDni(String dni) {

        MemberEntity member = memberRepository.findByDni(dni).
                orElseThrow(()->new ResourceNotFoundException("There is no member with DNI: "+ dni));

        List<RegistrationEntity> registrations = registrationRepository.findByMember(member);

        return registrations.stream()
                .map(reg -> RegistrationResponseDto.builder()
                        .id(reg.getId())
                        .className(reg.getClasses().getName())
                        .memberName(reg.getMember().getFirstName() + " " + reg.getMember().getLastName())
                        .ScheduleName(reg.getSchedule().getDay().name() + ": " +
                                reg.getSchedule().getStartTime() + " - " + reg.getSchedule().getEndTime())
                        .build()
                )
                .collect(Collectors.toList());

    }

    @Override
    public void deleteById(Long id) {

        RegistrationEntity registration = registrationRepository.findById(id)
                        .orElseThrow(()->new ResourceNotFoundException("There is no registration with ID: "+id));

        registrationRepository.delete(registration);
    }
}
