package com.inventoryservice.dto;

import com.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMapper {
    public Inventory createInventoryRequestToInventory(CreateInventoryRequest createInventoryRequest){
        return Inventory.builder()
                .productId(createInventoryRequest.getProductId())
                .quantity(createInventoryRequest.getQuantity())
                .build();
    }

}
