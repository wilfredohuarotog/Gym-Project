package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.requests.MemberDto;
import com.gym_app.gym_app.dto.requests.MemberUpdateDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import com.gym_app.gym_app.services.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gym/member")
public class MemberController {

    public final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAllMember() {
        //return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok(memberService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> saveMember(@Valid @RequestBody MemberDto memberDto) {
        memberService.saveMember(memberDto);
        //return new ResponseEntity<>("Member saved",HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body("Member saved");
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@Valid @RequestBody MemberUpdateDto memberDto, @PathVariable Long id) {
        return ResponseEntity.ok(memberService.updateMember(memberDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<ActiveMemberResponseDto> activeMember(@RequestParam("dni") String dni) {
        return ResponseEntity.ok(memberService.isMemberActive(dni));
    }
}
