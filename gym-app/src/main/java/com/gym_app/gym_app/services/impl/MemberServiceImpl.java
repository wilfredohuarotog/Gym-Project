package com.gym_app.gym_app.services.impl;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import com.gym_app.gym_app.entities.AgreementEntity;
import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.exceptions.ResourceNotFoundException;
import com.gym_app.gym_app.mapper.MemberMapper;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.repositories.MemberShipRepository;
import com.gym_app.gym_app.services.AgreementCreationService;
import com.gym_app.gym_app.services.MemberService;
import com.gym_app.gym_app.messaging.PublishEventsMemberService;
import com.gym_app.gym_app.validators.MemberValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MemberValidatorService memberValidatorService;
    private final AgreementCreationService agreementCreationService;

    @Override
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toMemberResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MemberResponseDto saveMember(MemberDto memberDto) {

        //Se valida que el DNI, número de telefónico y el correo sean unicos
        memberValidatorService.validateNewMember(memberDto);

        //Se crea el miembro y su contrato
        MemberEntity member = createMemberAndAgreement(memberDto);

        //Persistencia del miembro
        MemberEntity savedMember = memberRepository.save(member);

        MemberResponseDto memberResponse = memberMapper.toMemberResponseDto(savedMember);

        //Publicación del evento de guardado de nuevo miembro
        publishEventsMemberService.publishEventNewMemberNotification(
                memberResponse
        );
        return memberResponse;
    }

    @Override
    public MemberResponseDto findById(Long id) {

        MemberEntity member = getMemberById(id);
        return memberMapper.toMemberResponseDto(member);
    }

    @Override
    public MemberResponseDto updateMember(MemberUpdateDto memberDto, Long id) {

        MemberEntity member = getMemberById(id);
        memberValidatorService.validateMemberUpdate(memberDto, id);

        memberMapper.updateEntityFromDto(memberDto, member);
        MemberEntity updatedMember = memberRepository.save(member);

        return memberMapper.toMemberResponseDto(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {

        MemberEntity member = getMemberById(id);
        memberRepository.delete(member);
    }

    @Override
    public ActiveMemberResponseDto isMemberActive(String dni) {

        MemberEntity member = getMemberByDni(dni);
        return memberMapper.toActiveMemberDto(member);
    }

    @Override
    public void expiredMembers() {

        LocalDate today = LocalDate.now();
        List<MemberEntity> expiredMembers =
                memberRepository.findByStatusAndAgreementEndDateBefore(MemberStatus.ACTIVE, today);

        if (expiredMembers.isEmpty()) {
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


    //Metodos auxilires

    private MemberEntity createMemberAndAgreement(MemberDto memberDto) {

        MemberEntity member = memberMapper.toEntity(memberDto);

        MemberShipEntity memberShip = getMemberShipById(memberDto.agreement().memberShipId());

        AgreementEntity agreement = agreementCreationService.createAgreement(memberShip, member, memberDto.agreement().months());

        member.setAgreement(agreement);
        member.setStatus(MemberStatus.ACTIVE);

        return member;
    }

    private MemberEntity getMemberById(Long id) {

        return memberRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Member with ID: "+ id +" doesn't exist"));
    }

    private MemberShipEntity getMemberShipById(Long id) {

        return memberShipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no membership with this ID"));
    }

    private MemberEntity getMemberByDni(String dni) {

        return memberRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Member with DNI: " + dni + "doesn't exist"));
    }

}
