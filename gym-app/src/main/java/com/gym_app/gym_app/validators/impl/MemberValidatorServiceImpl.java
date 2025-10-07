package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.validators.MemberValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberValidatorServiceImpl implements MemberValidatorService {

    private final MemberRepository memberRepository;

    @Override
    public void validateNewMember(MemberDto memberDto) {

        validateUniqueDni(memberDto.dni(),null);
        validateUniquePhone(memberDto.phoneNumber(),null);
        validateUniqueEmail(memberDto.email(),null);

    }

    @Override
    public void validateMemberUpdate(MemberUpdateDto memberDto, Long id) {

        validateUniqueDni(memberDto.dni(), id);
        validateUniquePhone(memberDto.phoneNumber(), id);
        validateUniqueEmail(memberDto.email(), id);

    }

    private void validateUniqueDni(String dni, Long id) {

        boolean response = (id == null) ? memberRepository.existsByDni(dni) : memberRepository.existsByDniAndIdNot(dni, id);

        if (response){
            throw new BadRequestException("A member with this DNI already exists");
        }
    }

    private void validateUniquePhone(String phone, Long id) {

        boolean response = (id == null) ? memberRepository.existsByPhoneNumber(phone) : memberRepository.existsByPhoneNumberAndIdNot(phone, id);

        if (response) {
            throw new BadRequestException("A member with this phone already exists");
        }
    }

    private void validateUniqueEmail(String email, Long id) {

        boolean response = (id == null) ? memberRepository.existsByEmail(email) : memberRepository.existsByEmailAndIdNot(email, id);

        if (response){

            throw new BadRequestException("A member with this email already exists");

        }
        
    }

}
