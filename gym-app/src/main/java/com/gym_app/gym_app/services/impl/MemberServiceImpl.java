package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.BadRequestException;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.MemberMapper;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.services.MemberService;
import com.gym_app.gym_app.services.PublishEventsMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberShipRepository memberShipRepository;
    private final MemberMapper memberMapper;
    private final PublishEventsMemberService publishEventsMemberService;
    //private final KafkaTemplate kafkaTemplate;

    @Override
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toMemberResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveMember(MemberDto memberDto) {

        if(memberRepository.existsByEmail(memberDto.email())){
            throw new BadRequestException("A member with this email already exists");
        }
        if(memberRepository.existsByPhoneNumber(memberDto.phoneNumber())){
            throw new BadRequestException("A member with this phone already exists");
        }
        if(memberRepository.existsByDni(memberDto.dni())){
            throw new BadRequestException("A member with this DNI already exists");
        }

        MemberEntity member = memberMapper.toEntity(memberDto);

        AgreementEntity agreement = AgreementEntity.builder()
                .memberShip(
                        memberShipRepository.findById(memberDto.agreement().memberShipId())
                                .orElseThrow(()->new BadRequestException("There is no membership with this ID"))
                )
                .months(memberDto.agreement().months())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(memberDto.agreement().months()))
                .member(member)
                .build();

        member.setAgreement(agreement);
        member.setStatus(MemberStatus.ACTIVE);
        memberRepository.save(member);

        //kafkaTemplate.send("new-member",memberMapper.toMemberResponseDto(member));
        publishEventsMemberService.publishEventNewMemberNotification(memberMapper.toMemberResponseDto(member));

    }

    @Override
    public MemberResponseDto findById(Long id) {

        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Member with ID: "+ id +" doesn't exist"));

        return memberMapper.toMemberResponseDto(member);
    }

    @Override
    public MemberResponseDto updateMember(MemberUpdateDto memberDto, Long id) {

        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Member with ID: "+ id +" doesn't exist"));

        if(memberRepository.existsByEmailAndIdNot(memberDto.email(),id)){
            throw new BadRequestException("A member with this email already exists");
        }
        if(memberRepository.existsByPhoneNumberAndIdNot(memberDto.phoneNumber(),id)){
            throw new BadRequestException("A member with this phone already exists");
        }
        if(memberRepository.existsByDniAndIdNot(memberDto.dni(),id)){
            throw new BadRequestException("A member with this DNI already exists");
        }

        memberMapper.updateEntityFromDto(memberDto,member);
        memberRepository.save(member);

        return memberMapper.toMemberResponseDto(member);
    }

    @Override
    public void deleteMember(Long id) {

        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Member with ID: "+ id +" doesn't exist"));

        memberRepository.delete(member);
    }

    @Override
    public ActiveMemberResponseDto isMemberActive(String dni) {

        MemberEntity member = memberRepository.findByDni(dni)
                .orElseThrow(()->new ResourceNotFoundException("Member with DNI: "+ dni + "doesn't exist"));

        return memberMapper.toActiveMemberDto(member);
    }

    @Override
    public void expiredMembers() {

        LocalDate today = LocalDate.now();
        List<MemberEntity> expiredMembers =
                memberRepository.findByStatusAndAgreementEndDateBefore(MemberStatus.ACTIVE, today);

        if (expiredMembers.isEmpty()){
            log.info("There are no expired members");
            return;
        }

        expiredMembers.forEach(expired -> {
                    expired.setStatus(MemberStatus.INACTIVE);
                    MemberResponseDto memberResponseDto = memberMapper.toMemberResponseDto(expired);
                    publishEventsMemberService.publishEventExpiredMemberNotification(memberResponseDto);
                }
            );

        memberRepository.saveAll(expiredMembers);
    }
}
