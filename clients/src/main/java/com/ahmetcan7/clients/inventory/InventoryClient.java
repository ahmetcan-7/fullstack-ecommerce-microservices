package com.ahmetcan7.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "inventory",
        url = "${clients.inventory.url}"
)
public interface InventoryClient {

    @PostMapping("/v1/inventories/isInStock")
    InventoryCheckResponse isInStock(List<InventoryCheckRequest> inventoryCheckRequest);
}
