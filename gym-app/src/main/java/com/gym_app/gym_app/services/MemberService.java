package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;

import java.util.List;

public interface MemberService {

    List<MemberResponseDto> findAll();

    void saveMember(MemberDto memberDto);

    MemberResponseDto findById(Long id);

    MemberResponseDto updateMember(MemberUpdateDto memberDto, Long id);

    void deleteMember(Long id);

    ActiveMemberResponseDto isMemberActive(String dni);

    void expiredMembers ();

}
