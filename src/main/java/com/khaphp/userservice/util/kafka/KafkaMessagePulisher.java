package com.khaphp.userservice.util.kafka;

import com.khaphp.common.dto.notification.NotificationDTOcreate;
import com.khaphp.userservice.constant.TopicEventKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaMessagePulisher {
    private final KafkaTemplate<String, Object> template;

    public void sendNotificationDTOcreate(NotificationDTOcreate object){
        CompletableFuture<SendResult<String, Object>> future = template.send(TopicEventKafka.NOTIFY_USER_BIRTHDAY.name(), object);
        future.whenComplete((result, ex) -> {
            if(ex == null){
               System.out.println("Send message=[" + object + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }else{
               System.out.println("Unable to send message=[" + object + "] due to : " + ex.getMessage());
            }
        });
    }
}
