package ru.java.crm.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.OrderDto;

@Component
public class OrderConsumer {

    @RabbitListener(queues = "${crm.messaging.consume.order.queue}")
    public void handleOrderCreated(OrderDto orderDto) {
        System.out.println("Order created: " + orderDto);
    }
}
