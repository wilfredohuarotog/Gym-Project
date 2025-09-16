package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.ScheduleDto;
import com.gym_app.gym_app.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> findAllSchedule(){
        return ResponseEntity.ok(scheduleService.findAllSchedule());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> saveSchedule(@RequestBody ScheduleDto scheduleDto){
        ScheduleDto schedule = scheduleService.saveSchedule(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule (@RequestBody ScheduleDto scheduleDto, @PathVariable Long id){
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
