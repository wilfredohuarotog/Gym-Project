package com.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.dto.MemberAgreementDto;
import com.notification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaListenerService {

    private final ObjectMapper objectMapper;
    private final EmailNotificationService emailNotificationService;

    @KafkaListener(topics = "new-member", groupId = "mygroup")
    public void sendEmailNewMember (String messageJson) {
        MemberAgreementDto memberAgreementDto = parserMemberAgreement(messageJson);
        emailNotificationService.sendEmailNewMember(memberAgreementDto);
    }

    @KafkaListener(topics = "membership-expired", groupId = "mygroup")
    public void sendEmailMemberShipExpired (String messageJson) {
        MemberAgreementDto memberAgreementDto = parserMemberAgreement(messageJson);
        emailNotificationService.sendEmailMemberShipExpired(memberAgreementDto);
    }

    private MemberAgreementDto parserMemberAgreement(String messageJson) {
        try {
            return objectMapper.readValue(messageJson, MemberAgreementDto.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse message: {}", messageJson);
            throw new RuntimeException(
                    String.format("Failed to parse message: %s", messageJson)
            );
        }
    }

}
