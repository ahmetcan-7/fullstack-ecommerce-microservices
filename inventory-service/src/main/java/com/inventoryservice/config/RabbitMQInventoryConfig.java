package com.inventoryservice.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQInventoryConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.inventory}")
    private String inventoryQueue;

    @Value("${rabbitmq.routing-keys.internal-inventory}")
    private String internalInventoryRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.inventoryQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalInventoryRoutingKey);
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getInventoryQueue() {
        return inventoryQueue;
    }

    public String getInternalInventoryRoutingKey() {
        return internalInventoryRoutingKey;
    }
}