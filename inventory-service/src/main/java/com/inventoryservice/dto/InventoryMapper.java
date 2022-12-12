package com.inventoryservice.dto;

import com.ahmetcan7.amqp.dto.InventoryRequest;
import com.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMapper {
    public Inventory createInventoryRequestToInventory(InventoryRequest inventoryRequest){
        return Inventory.builder()
                .productId(inventoryRequest.getProductId())
                .quantity(inventoryRequest.getQuantity())
                .build();
    }

}
