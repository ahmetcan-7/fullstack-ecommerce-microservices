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

    @Value("${rabbitmq.queues.create-inventory}")
    private String createInventoryQueue;

    @Value("${rabbitmq.routing-keys.create-inventory}")
    private String createInventoryRoutingKey;

    @Value("${rabbitmq.queues.delete-inventory}")
    private String deleteInventoryQueue;

    @Value("${rabbitmq.routing-keys.delete-inventory}")
    private String deleteInventoryRoutingKey;

    @Value("${rabbitmq.queues.update-inventory}")
    private String updateInventoryQueue;

    @Value("${rabbitmq.routing-keys.update-inventory}")
    private String updateInventoryRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue createInventoryQueue() {
        return new Queue(this.createInventoryQueue);
    }

    @Bean
    public Queue deleteInventoryQueue() {
        return new Queue(this.deleteInventoryQueue);
    }

    @Bean
    public Queue updateInventoryQueue() {
        return new Queue(this.updateInventoryQueue);
    }

    @Bean
    public Binding createInventoryBinding() {
        return BindingBuilder
                .bind(createInventoryQueue())
                .to(internalTopicExchange())
                .with(this.createInventoryRoutingKey);
    }

    @Bean
    public Binding deleteInventoryBinding() {
        return BindingBuilder
                .bind(deleteInventoryQueue())
                .to(internalTopicExchange())
                .with(this.deleteInventoryRoutingKey);
    }

    @Bean
    public Binding updateInventoryBinding() {
        return BindingBuilder
                .bind(updateInventoryQueue())
                .to(internalTopicExchange())
                .with(this.updateInventoryRoutingKey);
    }

}