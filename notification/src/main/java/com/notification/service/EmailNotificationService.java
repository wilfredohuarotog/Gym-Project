package com.notification.service;

import com.notification.dto.MemberAgreementDto;

public interface EmailNotificationService {

    void sendEmailNewMember(MemberAgreementDto memberAgreementDto);
    void sendEmailMemberShipExpired(MemberAgreementDto memberAgreementDto);
}
