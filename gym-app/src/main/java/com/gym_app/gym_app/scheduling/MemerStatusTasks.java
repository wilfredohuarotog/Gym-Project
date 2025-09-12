package com.gym_app.gym_app.scheduling;

import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.mapper.MemberMapper;
import com.gym_app.gym_app.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemerStatusTasks {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final KafkaTemplate kafkaTemplate;

    @Scheduled(cron = "0 0 6 * * *")
    public void deactivateExpiredMembers() {
        List<MemberEntity> expiredMembers =
                memberRepository.findByStatusAndAgreementEndDateBefore(MemberStatus.ACTIVE, LocalDate.now());

        if (!expiredMembers.isEmpty()) {
            expiredMembers.forEach(member -> {
                member.setStatus(MemberStatus.INACTIVE);
                kafkaTemplate.send("membership-expired",memberMapper.toMemberResponseDto(member)) ;
            });
            memberRepository.saveAll(expiredMembers);
        }
    }
}
