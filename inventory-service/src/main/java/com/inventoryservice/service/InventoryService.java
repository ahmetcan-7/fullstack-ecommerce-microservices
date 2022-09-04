package com.inventoryservice.service;

import com.ahmetcan7.amqp.InventoryRequest;
import com.inventoryservice.dto.InventoryMapper;
import com.inventoryservice.model.Inventory;
import com.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    public String addProductToInventory(InventoryRequest inventoryRequest){
        Inventory inventory = inventoryRepository.save(inventoryMapper.createInventoryRequestToInventory(inventoryRequest));
        return "Successful added to inventory";
    }
}
