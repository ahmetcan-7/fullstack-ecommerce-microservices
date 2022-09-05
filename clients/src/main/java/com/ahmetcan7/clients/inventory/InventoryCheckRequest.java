package com.ahmetcan7.clients.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InventoryCheckRequest {
    UUID productId;
    Integer quantity;
}
