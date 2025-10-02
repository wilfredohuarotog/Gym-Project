package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.requests.ClassesDto;
import com.gym_app.gym_app.dto.responses.ClassesResponseDto;
import com.gym_app.gym_app.services.ClassesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gym/class")
public class ClassesController {

    public final ClassesService classesService;

    @GetMapping
    public ResponseEntity<List<ClassesResponseDto>> findAllClasses() {
        return ResponseEntity.ok(classesService.findAllClasses());
        //return new ResponseEntity<>(classesService.findAllClasses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(classesService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createClass(@Valid @RequestBody ClassesDto classesDto) {
        classesService.saveClass(classesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Class created");
        //return new ResponseEntity<>("Class created",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassesResponseDto> updateClass (@Valid @RequestBody ClassesDto classesDto, @PathVariable Long id) {
        return ResponseEntity.ok(classesService.updateClass(classesDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        classesService.deleteCoach(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Class deleted");
    }

}
