package com.orderservice.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class InventoryCheckRequest {
    UUID productId;
    Integer quantity;

}
