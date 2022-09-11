package com.ahmetcan7.cartservice.dto.cartItem;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CartItemDto {
    private Long id;
    private UUID productId;
    private String name;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private Integer quantity;
}
