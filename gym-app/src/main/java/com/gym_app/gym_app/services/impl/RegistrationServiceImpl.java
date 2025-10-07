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
import com.gym_app.gym_app.validators.RegistrationValidatorService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final RegistrationValidatorService registrationValidatorService;

    @Override
    public List<RegistrationResponseDto> findAllRegistration() {
        return registrationRepository.findAll().stream()
                .map(registrationMapper::toRegistrationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RegistrationResponseDto saveRegistration(RegistrationDto registrationDto) {

        MemberEntity member = getMemberByDni(registrationDto.dni());
        ClassesEntity classes = getClassById(registrationDto.classesId());
        ScheduleEntity schedule = getScheduleById(registrationDto.scheduleId());

        registrationValidatorService.validateNewRegistration(member, classes, schedule);

        RegistrationEntity registration = createNewRegistration(classes, schedule, member);

        return registrationMapper.toRegistrationResponseDto(
                registrationRepository.save(registration)
        );
    }

    @Override
    public List<RegistrationResponseDto> registrationsByDni(String dni) {

        MemberEntity member = getMemberByDni(dni);

        List<RegistrationEntity> registrations = registrationRepository.findByMember(member);

        return registrations.stream()
                .map(registrationMapper::toRegistrationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {

        RegistrationEntity registration = getRegistrationById(id);

        try {
            registrationRepository.delete(registration);
        }
        catch (DataIntegrityViolationException e){
            throw new BadRequestException("Cannot delete schedule due to existing references in the database");
        }
    }

    @Override
    public void deleteExpiredRegistrations() {

        LocalDate today = LocalDate.now();
        List<RegistrationEntity> expired = registrationRepository.findByScheduleDay(today.getDayOfWeek());
        registrationRepository.deleteAll(expired);
    }


    //Metodos auxiliares

    private ScheduleEntity getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    private ClassesEntity getClassById(Long id) {
        return classesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class with this ID doesn't exist"));

    }

    private RegistrationEntity getRegistrationById (Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("There is no registration with ID: "+id));
    }

    private MemberEntity getMemberByDni(String dni) {
        return memberRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Member with this DNI doesn't exist"));
    }

    private RegistrationEntity createNewRegistration (ClassesEntity classes,
                                                      ScheduleEntity schedule,
                                                      MemberEntity member) {
        return RegistrationEntity.builder()
                .classes(classes)
                .schedule(schedule)
                .member(member)
                .build();
    }
}
