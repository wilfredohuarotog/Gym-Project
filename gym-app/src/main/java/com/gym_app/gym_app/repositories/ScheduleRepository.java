package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {

    List<ScheduleEntity> findByDay (DayOfWeek day);

    List<ScheduleEntity> findByDayAndIdNot (DayOfWeek day, Long id);
}
