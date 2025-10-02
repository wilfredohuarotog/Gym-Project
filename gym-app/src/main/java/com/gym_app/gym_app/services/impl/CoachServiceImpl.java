package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.CoachDto;
import com.gym_app.gym_app.entities.CoachEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.CoachMapper;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.CoachRepository;
import com.gym_app.gym_app.services.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de Coaches.
 *
 * Se encarga de la lógica de negocio relacionada con los entrenadores:
 * - Crear, actualizar y eliminar coaches.
 * - Buscar coaches individuales o listarlos todos.
 * -  Usa {@link CoachRepository} para el acceso a datos y {@link CoachMapper} para mapear entre entidades y DTOs.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final ClassesRepository classesRepository;
    private final CoachMapper coachMapper;

    /**
     * Recupera una lista de todos los entrenadores existentes.
     *
     * @return Una lista de {@link CoachDto} que representa a todos los entrenadores.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoachDto> findAllCoach() {

        return coachRepository.findAll().stream()
                .map(coachMapper::toDto)
                .toList();
    }

    /**
     * Busca un entrenador por su identificador único.
     *
     * @param id El ID del entrenador a buscar.
     * @return Un {@link CoachDto} que representa al entrenador encontrado.
     * @throws ResourceNotFoundException si no se encuentra un entrenador con el ID proporcionado.
     */
    @Override
    @Transactional(readOnly = true)
    public CoachDto findById(Long id) {

        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Coach with ID: "+ id+" doesn't exist"));

        return coachMapper.toDto(coach);
    }

    /**
     * Guarda un nuevo entrenador en la base de datos.
     *
     * @param coachDto El DTO que contiene los datos del entrenador a guardar.
     */
    @Override
    public CoachDto saveCoach(CoachDto coachDto) {
        CoachEntity coach = coachRepository.save(coachMapper.toEntity(coachDto));
        return coachMapper.toDto(coach) ;
    }

    /**
     * Actualiza los datos de un entrenador existente.
     *
     * @param coachDto El DTO con los nuevos datos del entrenador.
     * @param id       El ID del entrenador a actualizar.
     * @return El {@link CoachDto} con los datos actualizados del entrenador.
     * @throws ResourceNotFoundException si no se encuentra un entrenador con el ID proporcionado.
     */
    @Override
    public CoachDto updateCoach(CoachDto coachDto, Long id) {

        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Coach with ID: "+ id+" doesn't exist"));

        coachMapper.updateEntityFromDto(coachDto,coach);

        coachRepository.save(coach);

        return coachMapper.toDto(coach);
    }

    /**
     * Elimina un entrenador de la base de datos por su identificador.
     *
     * @param id El ID del entrenador a eliminar.
     * @throws ResourceNotFoundException si no se encuentra un entrenador con el ID proporcionado.
     */
    @Override
    public void deleteCoach(Long id) {

        if(classesRepository.existsByCoachId(id)){
            throw new BadRequestException("Cannot delete coach. There are classes assigned to this coach");
        }

        CoachEntity coach = coachRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Coach with ID: "+ id+" doesn't exist"));

        try{
            coachRepository.delete(coach);
        } catch (DataIntegrityViolationException e){
            throw new BadRequestException("Cannot delete coach due to existing references in the database");
        }

    }
}
