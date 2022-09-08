package com.ahmetcan7.cartservice.dto;

import com.ahmetcan7.cartservice.model.CartItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartDto {
    private Long id;
    private UUID customerId;
    private List<CartItem> cartItems;
    private BigDecimal totalPrice;
}
