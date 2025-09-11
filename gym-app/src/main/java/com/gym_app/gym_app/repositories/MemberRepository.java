package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.RegistrationEntity;
import com.gym_app.gym_app.entities.emuns.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    boolean existsByDni(String dni);
    boolean existsByEmail (String email);
    boolean existsByPhoneNumber(String phone);
    boolean existsByDniAndIdNot(String dni, Long id);
    boolean existsByEmailAndIdNot (String email, Long id);
    boolean existsByPhoneNumberAndIdNot(String phone, Long id);
    Optional<MemberEntity> findByDni(String dni);
    List<MemberEntity> findByStatusAndAgreement_EndDateBefore(MemberStatus status, LocalDate date);
}
