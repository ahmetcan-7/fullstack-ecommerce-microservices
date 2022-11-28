package com.inventoryservice.controller;

import com.inventoryservice.dto.InventoryCheckRequest;
import com.inventoryservice.dto.InventoryCheckResponse;
import com.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/inventories")
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/isInStock")
    public ResponseEntity<InventoryCheckResponse> isInStock(@RequestBody List<InventoryCheckRequest> inventoryCheckRequests){
        return ResponseEntity.ok(inventoryService.isInStock(inventoryCheckRequests));
    }

}
