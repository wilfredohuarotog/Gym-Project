package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.CoachDto;
import com.gym_app.gym_app.entities.CoachEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.CoachMapper;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.CoachRepository;
import com.gym_app.gym_app.services.CoachService;
import com.gym_app.gym_app.validators.CoachValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final CoachValidatorService coachValidatorService;

    @Override
    @Transactional(readOnly = true)
    public List<CoachDto> findAllCoach() {

        return coachRepository.findAll().stream()
                .map(coachMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CoachDto findById(Long id) {

        CoachEntity coach = getCoachById(id);

        return coachMapper.toDto(coach);
    }

    @Override
    @Transactional
    public CoachDto saveCoach(CoachDto coachDto) {

        coachValidatorService.validateNewCoach(coachDto);

        CoachEntity coach = coachRepository.save(coachMapper.toEntity(coachDto));
        return coachMapper.toDto(coach);
    }

    @Override
    @Transactional
    public CoachDto updateCoach(CoachDto coachDto, Long id) {

        CoachEntity coach = getCoachById(id);

        coachValidatorService.validateUpdateCoach(coachDto, id);

        coachMapper.updateEntityFromDto(coachDto,coach);

        CoachEntity savedCoach = coachRepository.save(coach);

        return coachMapper.toDto(savedCoach);
    }

    @Override
    @Transactional
    public void deleteCoach(Long id) {

        CoachEntity coach = getCoachById(id);

        try{
            coachRepository.delete(coach);
        } catch (DataIntegrityViolationException e){
            throw new BadRequestException("Cannot delete coach due to existing references in the database");
        }

    }


    //Metodo auxiliares

    private CoachEntity getCoachById(Long id) {

        return coachRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Coach with ID: "+ id+" doesn't exist"));

    }
}
