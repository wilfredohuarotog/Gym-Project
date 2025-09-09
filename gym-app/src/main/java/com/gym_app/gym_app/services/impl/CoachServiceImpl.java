package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.CoachDto;
import com.gym_app.gym_app.entities.CoachEntity;
import com.gym_app.gym_app.mapper.CoachMapper;
import com.gym_app.gym_app.repositories.CoachRepository;
import com.gym_app.gym_app.services.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;

    @Override
    public List<CoachDto> findAllCoach() {

        return coachRepository.findAll().stream()
                .map(coachMapper::toDto)
                .toList();
    }

    @Override
    public CoachDto findById(Long id) {

        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("GAAA"));

        return coachMapper.toDto(coach);
    }

    @Override
    public void saveCoach(CoachDto coachDto) {

        coachRepository.save(coachMapper.toEntity(coachDto));
    }

    @Override
    public CoachDto updateCoach(CoachDto coachDto, Long id) {

        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(()->new RuntimeException("gaaaa"));

        coachMapper.updateEntityFromDto(coachDto,coach);

        coachRepository.save(coach);

        return coachMapper.toDto(coach);
    }

    @Override
    public void deleteCoach(Long id) {

        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("raaaaa"));

        coachRepository.delete(coach);
    }
}
