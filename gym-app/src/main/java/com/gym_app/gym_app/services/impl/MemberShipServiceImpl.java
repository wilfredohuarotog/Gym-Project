package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.MemberShipDto;
import com.gym_app.gym_app.dto.responses.MemberShipResponseDto;
import com.gym_app.gym_app.entities.ClassesEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.mapper.MemberShipMapper;
import com.gym_app.gym_app.repositories.ClassesRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.services.MemberShipService;
import com.gym_app.gym_app.validators.MemberShipValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberShipServiceImpl implements MemberShipService {

    private final MemberShipRepository memberShipRepository;
    private final ClassesRepository classesRepository;
    private final MemberShipMapper memberShipMapper;
    private final MemberShipValidatorService memberShipValidatorService;

    @Override
    @Transactional(readOnly = true)
    public List<MemberShipResponseDto> findAll() {
        return memberShipRepository.findAll().stream()
                .map(memberShipMapper::toMemberShipResponseDto)
                .toList();
    }

    @Override
    public MemberShipResponseDto findMemberShipById(Long id) {
        MemberShipEntity memberShip = getMemberShipById(id);
        return memberShipMapper.toMemberShipResponseDto(memberShip);
    }

    @Override
    @Transactional
    public MemberShipResponseDto saveMemberShip(MemberShipDto memberShipDto) {

        memberShipValidatorService.validateNewMembership(memberShipDto);

        List<ClassesEntity> classes = getClassesForMemberShip(memberShipDto.classesId());

        MemberShipEntity savedMemberShip = memberShipRepository.save(
                createNewMemberShip(memberShipDto.name(), classes)
        );

        return memberShipMapper.toMemberShipResponseDto(savedMemberShip);
    }

    @Override
    @Transactional
    public MemberShipResponseDto updateMemberShip(MemberShipDto memberShipDto, Long id) {

        MemberShipEntity memberShip = getMemberShipById(id);

        memberShipValidatorService.validateUpdateMemberShip(memberShipDto, id);

        List<ClassesEntity> classes = getClassesForMemberShip(memberShipDto.classesId());

        MemberShipEntity updatedMemberShip = memberShipRepository.save(
                updateExistingMemberShip(memberShip, memberShipDto.name(), classes)
        );
        return memberShipMapper.toMemberShipResponseDto(updatedMemberShip);
    }

    @Override
    public void deleteMemberShip(Long id) {

        MemberShipEntity memberShip = getMemberShipById(id);

        try {
            memberShipRepository.delete(memberShip);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Cannot delete class due to existing reference in the database");
        }
    }

    //Metodos auxiliares

    private List<ClassesEntity> getClassesForMemberShip(List<Long> classesId) {

        return classesId.stream()
                .map(this::getClassesById)
                .collect(Collectors.toList());
    }

    private ClassesEntity getClassesById(Long id) {
         return classesRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no class with this ID: " + id));
    }

    private MemberShipEntity getMemberShipById(Long id) {
        return memberShipRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("there is no membership with this ID: " + id));
    }

    private MemberShipEntity createNewMemberShip(String name, List<ClassesEntity> classes) {

        return MemberShipEntity.builder()
                .name(name)
                .classes(classes)
                .build();
    }

    private MemberShipEntity updateExistingMemberShip(MemberShipEntity memberShip, String name, List<ClassesEntity> classes) {

        memberShip.setName(name);
        memberShip.setClasses(classes);
        return memberShip;
    }
}
