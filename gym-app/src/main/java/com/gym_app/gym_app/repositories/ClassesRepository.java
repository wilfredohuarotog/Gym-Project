package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.ClassesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<ClassesEntity,Long> {

    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsByScheduleIdAndIdNot(Long scheduleId, Long id);
    boolean existsById(Long id);
    Optional<ClassesEntity> findBySchedule_Id(Long scheduleId);
}
