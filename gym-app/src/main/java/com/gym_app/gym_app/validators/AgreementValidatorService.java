package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.AgreementRenewalDto;
import com.gym_app.gym_app.entities.AgreementEntity;

public interface AgreementValidatorService {

    void validateRenewalAgreement(AgreementEntity agreement);
}
