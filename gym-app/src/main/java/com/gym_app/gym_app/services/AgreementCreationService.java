package com.gym_app.gym_app.services;

import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;

public interface AgreementCreationService {

    public AgreementEntity createAgreement(MemberShipEntity memberShip, MemberEntity member, Long months);
}
