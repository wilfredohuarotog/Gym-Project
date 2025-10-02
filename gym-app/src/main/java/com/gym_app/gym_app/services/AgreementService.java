package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.requests.AgreementRenewalDto;
import com.gym_app.gym_app.dto.responses.AgreementRenewalResponseDto;

public interface AgreementService {

    AgreementRenewalResponseDto agreementRenewalById (AgreementRenewalDto agreementRenewalDto, Long id);


}
