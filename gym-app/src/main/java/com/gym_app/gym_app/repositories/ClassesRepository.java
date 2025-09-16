package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.ClassesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<ClassesEntity,Long> {

//    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
//            "FROM ClassesEntity c JOIN c.schedule s " +
//            "WHERE s.id = :scheduleId")
//    boolean existsScheduleInAnyClass(@Param("scheduleId") Long scheduleId);

    boolean existsByName(String name);
    boolean existsByScheduleIdAndIdNot(Long scheduleId, Long id);
    boolean existsById(Long id);
    Optional<ClassesEntity> findBySchedule_Id(Long scheduleId);
    boolean existsByCoachId(Long id);
}
