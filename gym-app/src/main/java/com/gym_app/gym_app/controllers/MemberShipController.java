package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.MemberShipDto;
import com.gym_app.gym_app.dto.responses.MemberShipResponseDto;
import com.gym_app.gym_app.services.MemberShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/membership")
public class MemberShipController {

    private final MemberShipService memberShipService;

    @GetMapping
    public ResponseEntity<List<MemberShipResponseDto>> findAll(){
        return new ResponseEntity<>(memberShipService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveMemberShip(@RequestBody MemberShipDto memberShipDto){
        memberShipService.saveMemberShip(memberShipDto);
        return new ResponseEntity<>("Membership saved",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberShipResponseDto> updateMemberShip (@RequestBody MemberShipDto memberShipDto, @PathVariable Long id){
        return ResponseEntity.ok(memberShipService.updateMemberShip(memberShipDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemberShip(@PathVariable Long id){
        memberShipService.deleteMemberShip(id);
        return ResponseEntity.ok("Membership deleted");
    }
}
