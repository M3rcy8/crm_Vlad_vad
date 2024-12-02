package ru.java.crm.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "crm.messaging")
public class RabbitMQProperties {

    private DirectionConsume consume;
    private DirectionProduce produce;

    @Data
    public static class DirectionConsume {
        @NotBlank
        private String exchange;
        @NotNull
        private Routing user;
        @NotNull
        private Routing deal;
        @NotNull
        private Routing order;
    }

    @Data
    public static class DirectionProduce {
        @NotNull
        private ProduceRouting user;
        @NotNull
        private ProduceRouting deal;
        @NotNull
        private ProduceRouting dealStatusUpdated;
        @NotNull
        private ProduceRouting order;
    }

    @Data
    public static class Routing {
        @NotBlank
        private String queue;
        @NotBlank
        private String routingKey;
    }

    @Data
    public static class ProduceRouting {
        @NotBlank
        private String exchange;
        @NotBlank
        private String routingKey;
    }
}
