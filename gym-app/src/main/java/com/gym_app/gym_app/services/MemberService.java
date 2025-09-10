package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.MemberDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import com.gym_app.gym_app.entities.MemberEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    List<MemberResponseDto> findAll();

    void saveMember(MemberDto memberDto);

    MemberResponseDto findById(Long id);

    MemberResponseDto updateMember(MemberDto memberDto, Long id);

    void deleteMember(Long id);

    ActiveMemberResponseDto isMemberActive(String dni);



}
