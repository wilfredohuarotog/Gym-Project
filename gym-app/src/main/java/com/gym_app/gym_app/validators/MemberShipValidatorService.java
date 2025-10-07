package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.MemberShipDto;

public interface MemberShipValidatorService {

    void validateNewMembership(MemberShipDto memberShipDto);

    void validateUpdateMemberShip(MemberShipDto memberShipDto, Long id);

}
