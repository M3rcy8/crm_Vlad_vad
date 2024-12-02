package ru.java.crm.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.DealDto;

@Component
public class DealConsumer {

    @RabbitListener(queues = "${crm.messaging.consume.deal.queue}")
    public void handleDealCreated(DealDto dealDto) {
        System.out.println("Deal created: " + dealDto);
    }
}
