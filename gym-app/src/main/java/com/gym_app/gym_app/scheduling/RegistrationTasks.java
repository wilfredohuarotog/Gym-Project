package com.gym_app.gym_app.scheduling;

import com.gym_app.gym_app.entities.RegistrationEntity;
import com.gym_app.gym_app.repositories.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegistrationTasks {

    public final RegistrationRepository registrationRepository;

    @Scheduled(cron = "0 42 11 * * *")
    public void deleteExpiredRegistrations(){
        LocalDate today = LocalDate.now();

        List<RegistrationEntity> expired = registrationRepository.findByScheduleDay(today.getDayOfWeek());

        registrationRepository.deleteAll(expired);
    }



}
