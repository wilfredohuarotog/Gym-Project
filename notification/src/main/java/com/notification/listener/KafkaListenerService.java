package com.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.dto.MemberAgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListenerService {

    private final JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper;
    @Value("${spring.mail.username}")
    private String email;

    @KafkaListener(topics = "new-member", groupId = "mygroup")
    public void sendEmailNewMember (String messageJson) throws JsonProcessingException {

        MemberAgreementDto memberAgreementDto = objectMapper.readValue(messageJson, MemberAgreementDto.class);

        String message = "Welcome "+ memberAgreementDto.name()+". Your agreement ID: "+memberAgreementDto.agreementId();

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(email);
        mailMessage.setTo(memberAgreementDto.email());
        mailMessage.setSubject("WELCOMEEEE");
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);

    }

    @KafkaListener(topics = "membership-expired", groupId = "mygroup")
    public void sendEmailMemberShipExpired (String messageJson) throws JsonProcessingException {

        MemberAgreementDto memberAgreementDto = objectMapper.readValue(messageJson, MemberAgreementDto.class);

        String message = "Good morning "+ memberAgreementDto.name()+". Your membership expired";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(email);
        mailMessage.setTo(memberAgreementDto.email());
        mailMessage.setSubject("MEMBERSHIP EXPIRED");
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);

    }
}
