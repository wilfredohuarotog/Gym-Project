package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<AgreementEntity,Long> {
}
