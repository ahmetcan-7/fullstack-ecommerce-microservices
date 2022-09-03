package com.inventoryservice.service;

import com.inventoryservice.dto.CreateInventoryRequest;
import com.inventoryservice.dto.InventoryMapper;
import com.inventoryservice.model.Inventory;
import com.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    @Transactional
    public String addProductToInventory(CreateInventoryRequest createInventoryRequest){
        Inventory inventory = inventoryRepository.save(inventoryMapper.createInventoryRequestToInventory(createInventoryRequest));
        return "Successful added to inventory";
    }
}
