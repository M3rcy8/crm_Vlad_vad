package ru.java.crm.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitConfiguration {

    private final RabbitMQProperties properties;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jsonMessageConverter) {
        final var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public Declarables declarables() {
        var declarables = new ArrayList<Declarable>();
        var consume = properties.getConsume();
        var produce = properties.getProduce();

        // Add consume exchanges and queues
        declarables.add(declarableExchange(consume.getExchange()));
        declarables.add(declarableQueue(consume.getUser().getQueue()));
        declarables.add(declarableQueue(consume.getDeal().getQueue()));
        declarables.add(declarableQueue(consume.getOrder().getQueue()));

        // Add produce exchanges
        declarables.add(declarableExchange(produce.getUser().getExchange()));
        declarables.add(declarableExchange(produce.getDeal().getExchange()));
        declarables.add(declarableExchange(produce.getDealStatusUpdated().getExchange()));
        declarables.add(declarableExchange(produce.getOrder().getExchange()));

        // Add bindings for user queue
        declarables.add(new Binding(
                consume.getUser().getQueue(),
                Binding.DestinationType.QUEUE,
                produce.getUser().getExchange(),
                produce.getUser().getRoutingKey(),
                null
        ));

        // Add bindings for deal queue
        declarables.add(new Binding(
                consume.getDeal().getQueue(),
                Binding.DestinationType.QUEUE,
                produce.getDeal().getExchange(),
                produce.getDeal().getRoutingKey(),
                null
        ));

        // Add bindings for order queue
        declarables.add(new Binding(
                consume.getOrder().getQueue(),
                Binding.DestinationType.QUEUE,
                produce.getOrder().getExchange(),
                produce.getOrder().getRoutingKey(),
                null
        ));

        return new Declarables(declarables);
    }

    private Exchange declarableExchange(final String name) {
        return new TopicExchange(name, true, false);
    }

    private Queue declarableQueue(final String name) {
        return new Queue(name, true);
    }
}
