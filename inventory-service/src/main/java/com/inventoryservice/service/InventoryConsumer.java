package com.inventoryservice.service;

import com.ahmetcan7.amqp.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @RabbitListener(queues = "${rabbitmq.queues.create-inventory}")
    public void createInventoryConsumer(InventoryRequest inventoryRequest) {
        log.info("Consumed {} from create-inventory queue", inventoryRequest);
        inventoryService.addProductToInventory(inventoryRequest);
    }

    @RabbitListener(queues = "${rabbitmq.queues.delete-inventory}")
    public void deleteInventoryConsumer(DeleteInventoryRequest deleteInventoryRequest) {
        log.info("Consumed {} from delete-inventory queue", deleteInventoryRequest);
        inventoryService.deleteProductFromInventory(deleteInventoryRequest);
    }

    @RabbitListener(queues = "${rabbitmq.queues.update-inventory}")
    public void updateInventoryConsumer(InventoryRequest inventoryRequest) {
        log.info("Consumed {} from update-inventory queue", inventoryRequest);
        inventoryService.updateProductFromInventory(inventoryRequest);
    }
}