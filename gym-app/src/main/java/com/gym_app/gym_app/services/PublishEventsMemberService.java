package com.gym_app.gym_app.services;

import com.gym_app.gym_app.dto.responses.MemberResponseDto;

public interface PublishEventsMemberService {

    void publishEventExpiredMemberNotification(MemberResponseDto memberResponseDto);

    void publishEventNewMemberNotification(MemberResponseDto memberResponseDto);

}
