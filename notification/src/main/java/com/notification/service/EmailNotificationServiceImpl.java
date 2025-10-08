package com.notification.service;

import com.notification.dto.MemberAgreementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailNotificationServiceImpl implements EmailNotificationService{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sourceEmail;

    private static final String SUBJECT_FOR_NEW_MEMBER = "Welcome to WILL FIT gym";
    private static final String SUBJECT_FOR_MEMBERSHIP_EXPIRED = "Membership expired";

    @Override
    public void sendEmailNewMember(MemberAgreementDto memberAgreementDto) {

        String bodyText = String.format(
                "Welcome!!! %s.%n%n" +
                        "Your agreement ID: %d.%n%n"+
                        "Best regards.%n" +
                        "Will Fit Corp.",
                memberAgreementDto.name(),
                memberAgreementDto.agreementId()
        );

        doSendEmail(memberAgreementDto.email(), SUBJECT_FOR_NEW_MEMBER, bodyText);
    }


    @Override
    public void sendEmailMemberShipExpired(MemberAgreementDto memberAgreementDto) {

        String bodyText = String.format(
                "Hello! %s.%n%n" +
                        "Your membership has expired :c. You can call 987654321 to renew.%n%n" +
                        "Best regards.%n" +
                        "Will Fit Corp.",
                memberAgreementDto.name()
        );

        doSendEmail(memberAgreementDto.email(), SUBJECT_FOR_MEMBERSHIP_EXPIRED, bodyText);
    }

    //Metodo auxiliar

    private void doSendEmail(String to, String subject, String text) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sourceEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            log.error("Failed to send email to {} with subject: {}", to, subject);
            throw new RuntimeException("Email sending failed");
        }
    }


}
