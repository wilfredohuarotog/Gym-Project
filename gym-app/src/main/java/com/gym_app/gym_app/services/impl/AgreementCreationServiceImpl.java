package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.services.AgreementCreationService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AgreementCreationServiceImpl implements AgreementCreationService {

    @Override
    public AgreementEntity createAgreement(MemberShipEntity memberShip, MemberEntity member, Long months) {

        return AgreementEntity.builder()
                .memberShip(memberShip)
                .months(months)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(months))
                .member(member)
                .build();
    }
}
