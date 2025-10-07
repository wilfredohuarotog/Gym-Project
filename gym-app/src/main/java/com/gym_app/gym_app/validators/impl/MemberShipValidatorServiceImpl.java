package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.MemberShipDto;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.validators.MemberShipValidatorService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberShipValidatorServiceImpl implements MemberShipValidatorService {

    private final MemberShipRepository memberShipRepository;

    @Override
    public void validateNewMembership(MemberShipDto memberShipDto) {

        validateUniqueName(memberShipDto.name(), null);
    }

    @Override
    public void validateUpdateMemberShip(MemberShipDto memberShipDto, Long id) {

        validateUniqueName(memberShipDto.name(), id);
    }

    private void validateUniqueName(String name, Long id) {

        boolean exist = (id == null) ?
                memberShipRepository.existsByName(name) :
                memberShipRepository.existsByNameAndIdNot(name, id);

        if (exist) {
            throw new BadRequestException("The membership with this name already exists");
        }
    }
}
