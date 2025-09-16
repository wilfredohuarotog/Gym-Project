package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.AgreementRenewalDto;
import com.gym_app.gym_app.dto.responses.AgreementRenewalResponseDto;
import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.AgreementMapper;
import com.gym_app.gym_app.repositories.AgreementRepository;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.services.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {


    private final AgreementRepository agreementRepository;
    private final MemberShipRepository memberShipRepository;
    private final AgreementMapper agreementMapper;

    @Override
    public AgreementRenewalResponseDto agreementRenewalById (AgreementRenewalDto agreementRenewalDto, Long id) {

        AgreementEntity agreement = agreementRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Agreement with ID: "+ id+ "doesn't exist"));

        MemberShipEntity memberShip = memberShipRepository.findById(agreementRenewalDto.memberShipId())
                .orElseThrow(()-> new BadRequestException("MemberShip with ID: "+ agreementRenewalDto.memberShipId()+ "doesn't exist"));

        if(agreement.getMember().getStatus().equals(MemberStatus.ACTIVE)){
            throw new BadRequestException("the member still has current membership");
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(agreementRenewalDto.months());

        agreement.getMember().setStatus(MemberStatus.ACTIVE);
        agreement.setStartDate(startDate);
        agreement.setEndDate(endDate);
        agreement.setMonths(agreementRenewalDto.months());
        agreement.setMemberShip(memberShip);

        agreementRepository.save(agreement);

        return agreementMapper.toResponseDto(agreement);
    }
}
