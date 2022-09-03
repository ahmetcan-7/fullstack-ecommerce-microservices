package com.inventoryservice.controller;

import com.inventoryservice.dto.CreateInventoryRequest;
import com.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1/inventories")
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<String> addToInventory(@Valid @RequestBody CreateInventoryRequest createInventoryRequest){
        return new ResponseEntity<>(inventoryService.addProductToInventory(createInventoryRequest), HttpStatus.CREATED);
    }
}
