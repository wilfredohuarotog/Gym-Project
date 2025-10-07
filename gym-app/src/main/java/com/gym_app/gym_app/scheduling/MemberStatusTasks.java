package com.gym_app.gym_app.scheduling;

import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.mapper.MemberMapper;
import com.gym_app.gym_app.repositories.MemberRepository;
import com.gym_app.gym_app.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberStatusTasks {

    private final MemberService memberService;

    @Scheduled(cron = "0 0 6 * * *")
    public void deactivateExpiredMembers() {

        memberService.expiredMembers();
    }

}
