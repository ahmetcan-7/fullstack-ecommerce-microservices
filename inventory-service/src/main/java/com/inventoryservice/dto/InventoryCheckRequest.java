package com.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryCheckRequest {
    UUID productId;
    Integer quantity;
}
