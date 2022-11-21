package com.inventoryservice.service;

import com.ahmetcan7.amqp.InventoryRequest;
import com.ahmetcan7.clients.inventory.InventoryCheckRequest;
import com.ahmetcan7.clients.inventory.InventoryCheckResponse;
import com.inventoryservice.dto.CreateInventoryRequest;
import com.inventoryservice.dto.InventoryMapper;
import com.inventoryservice.model.Inventory;
import com.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    public void addProductToInventory(InventoryRequest inventoryRequest){
        Inventory inventory = inventoryRepository
                .save(inventoryMapper.createInventoryRequestToInventory(inventoryRequest));
    }

    public InventoryCheckResponse isInStock(List<InventoryCheckRequest> inventoryCheckRequests) {
        List<Inventory> inventories = inventoryCheckRequests.stream().map(inventoryRequest -> inventoryRepository
                .findByProductIdAndQuantityLessThan(inventoryRequest.getProductId(), inventoryRequest.getQuantity()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<UUID> inventorIds = inventories.stream()
                .map(Inventory::getProductId).collect(Collectors.toList());


        return InventoryCheckResponse.builder()
                .isNotInStockProductIds(inventorIds)
                .isInStock(inventorIds.size() == 0)
                .build();
    }
}
