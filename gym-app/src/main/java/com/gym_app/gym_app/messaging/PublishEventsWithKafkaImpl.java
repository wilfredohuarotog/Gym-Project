package com.gym_app.gym_app.messaging;

import com.gym_app.gym_app.dto.responses.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishEventsWithKafkaImpl implements PublishEventsMemberService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishEventExpiredMemberNotification(MemberResponseDto memberResponseDto) {
        kafkaTemplate.send("membership-expired",memberResponseDto);
    }

    @Override
    public void publishEventNewMemberNotification(MemberResponseDto memberResponseDto) {
        kafkaTemplate.send("new-member",memberResponseDto);
    }
}
