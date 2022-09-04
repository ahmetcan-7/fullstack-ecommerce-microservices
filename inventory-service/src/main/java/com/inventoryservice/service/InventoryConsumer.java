package com.inventoryservice.service;

import com.ahmetcan7.amqp.InventoryRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @RabbitListener(queues = "${rabbitmq.queues.inventory}")
    public void consumer(InventoryRequest inventoryRequest) {
        log.info("Consumed {} from queue", inventoryRequest);
        inventoryService.addProductToInventory(inventoryRequest);
    }
}