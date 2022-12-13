package com.ahmetcan7.notificationservice.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQNotificationConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.send-email}")
    private String sendEmailQueue;

    @Value("${rabbitmq.routing-keys.send-email}")
    private String sendEmailRoutingKey;


    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue sendEmailQueue() {
        return new Queue(this.sendEmailQueue);
    }

    @Bean
    public Binding sendEmailBinding() {
        return BindingBuilder
                .bind(sendEmailQueue())
                .to(internalTopicExchange())
                .with(this.sendEmailRoutingKey);
    }

}