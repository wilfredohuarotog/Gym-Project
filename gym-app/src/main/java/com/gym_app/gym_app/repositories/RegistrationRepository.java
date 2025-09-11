package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Long> {

    Long countByClassesIdAndScheduleId(Long classesId, Long ScheduleId);
    boolean existsByMemberIdAndClassesIdAndScheduleId(Long memberId, Long classesId, Long scheduleId);
    List<RegistrationEntity> findByMember(MemberEntity member);

}
