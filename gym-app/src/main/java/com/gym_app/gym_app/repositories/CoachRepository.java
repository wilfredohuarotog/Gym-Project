package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity,Long> {
}
