package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.requests.MemberShipDto;
import com.gym_app.gym_app.dto.responses.MemberShipResponseDto;
import com.gym_app.gym_app.services.MemberShipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/gym/membership")
public class MemberShipController {

    private final MemberShipService memberShipService;

    @GetMapping
    public ResponseEntity<List<MemberShipResponseDto>> findAll() {
        return ResponseEntity.ok(memberShipService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberShipResponseDto> findMemberShipByid(@PathVariable Long id) {
        return ResponseEntity.ok(memberShipService.findMemberShipById(id));
    }

    @PostMapping
    public ResponseEntity<MemberShipResponseDto> saveMemberShip(@Valid @RequestBody MemberShipDto memberShipDto) {
        MemberShipResponseDto memberShipResponseDto = memberShipService.saveMemberShip(memberShipDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberShipResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberShipResponseDto> updateMemberShip(@Valid @RequestBody MemberShipDto memberShipDto, @PathVariable Long id) {
        return ResponseEntity.ok(memberShipService.updateMemberShip(memberShipDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemberShip(@PathVariable Long id) {
        memberShipService.deleteMemberShip(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
