package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.AgreementRenewalDto;
import com.gym_app.gym_app.dto.responses.AgreementRenewalResponseDto;
import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.AgreementMapper;
import com.gym_app.gym_app.repositories.AgreementRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.services.AgreementService;
import com.gym_app.gym_app.validators.AgreementValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final MemberShipRepository memberShipRepository;
    private final AgreementMapper agreementMapper;
    private final AgreementValidatorService agreementValidatorService;

    @Override
    @Transactional
    public AgreementRenewalResponseDto agreementRenewalById(AgreementRenewalDto agreementRenewalDto, Long id) {

        AgreementEntity agreement = getAgreementById(id);

        MemberShipEntity memberShip = getMemberShipById(agreementRenewalDto.memberShipId());

        //Se valida si aún está vigente el contrato, si está vigente no permite la renovación aún
        agreementValidatorService.validateRenewalAgreement(agreement);

        //se actualiza el contrato
        renewalAgreement(agreement, memberShip, agreementRenewalDto.months());

        agreementRepository.save(agreement);

        return agreementMapper.toResponseDto(agreement);
    }

    private AgreementEntity getAgreementById(Long id) {

        return agreementRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Agreement with ID: "+ id + " doesn't exist"));
    }

    private MemberShipEntity getMemberShipById(Long id) {

        return memberShipRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("MemberShip with ID: "+ id + " doesn't exist"));
    }

    private void renewalAgreement(AgreementEntity agreement, MemberShipEntity memberShip, Long months) {

        LocalDate startDate = LocalDate.now();

        agreement.getMember().setStatus(MemberStatus.ACTIVE);
        agreement.setStartDate(startDate);
        agreement.setEndDate(startDate.plusMonths(months));
        agreement.setMonths(months);
        agreement.setMemberShip(memberShip);
    }

}
