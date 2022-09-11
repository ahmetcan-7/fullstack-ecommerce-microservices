package com.ahmetcan7.cartservice.dto.cartItem;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class CreateCartItemRequest {
    private UUID productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
