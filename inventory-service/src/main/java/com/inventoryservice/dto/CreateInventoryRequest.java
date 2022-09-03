package com.inventoryservice.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class CreateInventoryRequest {
    @NotNull
    private UUID productId;
    @NotNull
    private Integer quantity;
}
