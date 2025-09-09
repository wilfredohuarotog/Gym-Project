package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.MemberEntity;
import com.gym_app.gym_app.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByDni (String dni);
    Optional<MemberEntity> findByemail (String email);
}
