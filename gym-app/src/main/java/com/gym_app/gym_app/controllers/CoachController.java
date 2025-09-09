package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.CoachDto;
import com.gym_app.gym_app.services.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coach")
public class CoachController {

    private final CoachService coachService;

    @GetMapping
    public ResponseEntity<List<CoachDto>> findAllCoach(){
        return new ResponseEntity<>(coachService.findAllCoach(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(coachService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveCoach(@RequestBody CoachDto coachDto){
        coachService.saveCoach(coachDto);
        return new ResponseEntity<>("Coach saved",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachDto> updateCoach (@RequestBody CoachDto coachDto, @PathVariable Long id){
        return ResponseEntity.ok(coachService.updateCoach(coachDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Long id){
        coachService.deleteCoach(id);
        return ResponseEntity.ok("Coach deleted");
    }

}
