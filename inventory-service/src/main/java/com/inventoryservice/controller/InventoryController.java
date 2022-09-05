package com.inventoryservice.controller;

import com.ahmetcan7.amqp.InventoryRequest;
import com.ahmetcan7.clients.inventory.InventoryCheckRequest;
import com.ahmetcan7.clients.inventory.InventoryCheckResponse;
import com.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/inventories")
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

//    @PostMapping
//    public ResponseEntity<String> addToInventory(@RequestBody InventoryRequest inventoryRequest){
//        return new ResponseEntity<>(inventoryService.addProductToInventory(inventoryRequest), HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<InventoryCheckResponse> isInStock(@RequestBody List<InventoryCheckRequest> inventoryCheckRequests){
        return ResponseEntity.ok(inventoryService.isInStock(inventoryCheckRequests));
    }

}
