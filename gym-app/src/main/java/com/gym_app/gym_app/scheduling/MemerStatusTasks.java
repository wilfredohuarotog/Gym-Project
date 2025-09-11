package com.gym_app.gym_app.scheduling;

import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import com.gym_app.gym_app.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemerStatusTasks {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 40 17 * * *") // todos los días a la medianoche
    public void deactivateExpiredMembers() {
        List<MemberEntity> expiredMembers =
                memberRepository.findByStatusAndAgreement_EndDateBefore(MemberStatus.ACTIVE, LocalDate.now());

        expiredMembers.forEach(member -> member.setStatus(MemberStatus.INACTIVE));

        if (!expiredMembers.isEmpty()) {
            memberRepository.saveAll(expiredMembers);
            System.out.println("Se desactivaron " + expiredMembers.size() + " miembros vencidos.");
        }
    }
}
