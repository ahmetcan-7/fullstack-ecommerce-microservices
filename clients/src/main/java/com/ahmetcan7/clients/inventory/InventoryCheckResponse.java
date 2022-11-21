package com.ahmetcan7.clients.inventory;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@ToString
public class InventoryCheckResponse {
    List<UUID> isNotInStockProductIds;
    Boolean isInStock;
}