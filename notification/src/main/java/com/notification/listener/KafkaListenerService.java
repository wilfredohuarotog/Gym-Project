package com.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.dto.MemberAgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/*throws JsonProcessingException */
@Component
@RequiredArgsConstructor
public class KafkaListenerService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "new-member", groupId = "mygroup")
    public void sendEmail (String messageJson) throws JsonProcessingException {

        //MemberAgreementDto memberAgreementDto = (MemberAgreementDto) object;
        ObjectMapper mapper = new ObjectMapper();
        MemberAgreementDto memberAgreementDto = mapper.readValue(messageJson, MemberAgreementDto.class);

        String message = "Welcome "+ memberAgreementDto.name()+". Your agreement ID: "+memberAgreementDto.agreementId();

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("pruebaservicios2299@gmail.com");
        mailMessage.setTo("pruebaservicios2299@gmail.com");
        mailMessage.setSubject("WELCOMEEEE");
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);

    }
}
