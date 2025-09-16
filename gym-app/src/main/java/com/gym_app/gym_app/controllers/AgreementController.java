package com.gym_app.gym_app.controllers;

import com.gym_app.gym_app.dto.AgreementRenewalDto;
import com.gym_app.gym_app.dto.responses.AgreementRenewalResponseDto;
import com.gym_app.gym_app.services.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agreement")
public class AgreementController {

    private final AgreementService agreementService;

    @PutMapping ("/{id}")
    public ResponseEntity<AgreementRenewalResponseDto> agreementRenewal (@RequestBody AgreementRenewalDto agreementRenewalDto, @PathVariable Long id){

        return ResponseEntity.ok(agreementService.agreementRenewalById(agreementRenewalDto, id)) ;
    }


}
