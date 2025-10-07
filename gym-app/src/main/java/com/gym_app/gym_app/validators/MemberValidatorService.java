package com.gym_app.gym_app.validators;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;

public interface MemberValidatorService {

     void validateNewMember(MemberDto memberDto);
     void validateMemberUpdate(MemberUpdateDto memberDto, Long id);

}
