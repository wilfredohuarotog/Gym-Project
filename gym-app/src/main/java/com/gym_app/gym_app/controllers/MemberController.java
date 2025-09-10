package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.CoachDto;
import com.gym_app.gym_app.dto.MemberDto;
import com.gym_app.gym_app.dto.responses.ActiveMemberResponseDto;
import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import com.gym_app.gym_app.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    public final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAllMember(){
        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveMember(@RequestBody MemberDto memberDto){
        memberService.saveMember(memberDto);
        return new ResponseEntity<>("Member saved",HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember (@RequestBody MemberDto memberDto, @PathVariable Long id){
        return ResponseEntity.ok(memberService.updateMember(memberDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.ok("Coach deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<ActiveMemberResponseDto> activeMember(@RequestParam("dni") String dni){
        return ResponseEntity.ok(memberService.isMemberActive(dni));
    }




}
