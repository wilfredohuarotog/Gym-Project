package com.gym_app.gym_app.validators.impl;

import com.gym_app.gym_app.dto.requests.AgreementRenewalDto;
import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.validators.AgreementValidatorService;
import org.springframework.stereotype.Component;

@Component
public class AgreementValidatorServiceImpl implements AgreementValidatorService {
    @Override
    public void validateRenewalAgreement(AgreementEntity agreement) {

        if (agreement.getMember().getStatus().equals(MemberStatus.ACTIVE)) {
            throw new BadRequestException("the member still has current membership");
        }

    }
}
