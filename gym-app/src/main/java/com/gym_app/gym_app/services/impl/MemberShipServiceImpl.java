package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.MemberShipDto;
import com.gym_app.gym_app.dto.responses.MemberShipResponseDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.mapper.MemberShipMapper;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.services.MemberShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberShipServiceImpl implements MemberShipService {

    private final MemberShipRepository memberShipRepository;
    private final ClassesRepository classesRepository;
    private final MemberShipMapper memberShipMapper;

    @Override
    public List<MemberShipResponseDto> findAll() {
        return memberShipRepository.findAll().stream()
                .map(memberShipMapper::toMemberShipResponseDto)
                .toList();
    }

    @Override
    public void saveMemberShip(MemberShipDto memberShipDto) {

        if(memberShipRepository.existsByName(memberShipDto.name())){
            throw new BadRequestException("The membership with this name already exists");
        }

        List<ClassesEntity> classes = memberShipDto.classes_id().stream()
                .map(id ->
                        classesRepository.findById(id)
                                .orElseThrow(() -> new BadRequestException("There is no class with this ID: " + id))
                )
                .toList();

        memberShipRepository.save(MemberShipEntity.builder()
                .name(memberShipDto.name())
                .classes(classes)
                .build());
    }

    @Override
    public MemberShipResponseDto updateMemberShip(MemberShipDto memberShipDto, Long id) {

        MemberShipEntity memberShip = memberShipRepository.findById(id)
                .orElseThrow(()->new BadRequestException("there is no membership with this ID: "+id));

        if(memberShipRepository.existsByName(memberShipDto.name())&&!memberShipDto.name().equals(memberShip.getName())){
            throw new BadRequestException("The membership with this name already exists");
        }

        List<ClassesEntity> classes = memberShipDto.classes_id().stream()
                .map(idAux ->
                        classesRepository.findById(idAux)
                                .orElseThrow(() -> new BadRequestException("There is no class with this ID: " + idAux))
                )
                .toList();

        memberShip.setClasses(classes);
        return memberShipMapper.toMemberShipResponseDto(memberShip);
    }

    @Override
    public void deleteMemberShip(Long id) {

    }
}
