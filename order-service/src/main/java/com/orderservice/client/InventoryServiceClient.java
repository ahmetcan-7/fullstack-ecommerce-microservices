package com.orderservice.client;

import com.orderservice.dto.inventory.InventoryCheckRequest;
import com.orderservice.dto.inventory.InventoryCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "inventory-service",
        path = "/v1/inventories"
)
public interface InventoryServiceClient {

    @PostMapping("/isInStock")
    InventoryCheckResponse isInStock(List<InventoryCheckRequest> inventoryCheckRequest);
}
