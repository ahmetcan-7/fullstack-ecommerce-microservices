package com.inventoryservice.controller;

import com.ahmetcan7.amqp.InventoryRequest;
import com.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/inventories")
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<String> addToInventory(@RequestBody InventoryRequest inventoryRequest){
        return new ResponseEntity<>(inventoryService.addProductToInventory(inventoryRequest), HttpStatus.CREATED);
    }
}
