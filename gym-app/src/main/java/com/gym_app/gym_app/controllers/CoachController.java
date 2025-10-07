package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.requests.CoachDto;
import com.gym_app.gym_app.services.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gym/coach")
public class CoachController {

    private final CoachService coachService;

    @GetMapping
    public ResponseEntity<List<CoachDto>> findAllCoaches() {
        return ResponseEntity.ok(coachService.findAllCoach());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(coachService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CoachDto> saveCoach(@Valid @RequestBody CoachDto coachDto) {
        CoachDto savedCoach = coachService.saveCoach(coachDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCoach);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachDto> updateCoach(@Valid @RequestBody CoachDto coachDto, @PathVariable Long id) {
        return ResponseEntity.ok(coachService.updateCoach(coachDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }

}
