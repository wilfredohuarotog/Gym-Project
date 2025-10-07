package com.gym_app.gym_app.scheduling;

import com.gym_app.gym_app.services.RegistrationService;
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

    private final RegistrationService registrationService;

    @Scheduled(cron = "0 59 23 * * *")
    public void deleteExpiredRegistrations(){

        registrationService.deleteExpiredRegistrations();
    }

}
