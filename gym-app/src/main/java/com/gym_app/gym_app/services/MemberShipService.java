package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.requests.MemberShipDto;
import com.gym_app.gym_app.dto.responses.MemberShipResponseDto;

import java.util.List;

public interface MemberShipService {

    List<MemberShipResponseDto> findAll();

    void saveMemberShip(MemberShipDto memberShipDto);

    MemberShipResponseDto updateMemberShip(MemberShipDto memberShipDto, Long id);

    void deleteMemberShip(Long id);

}
