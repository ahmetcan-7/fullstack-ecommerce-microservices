package com.orderservice.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotNull
    private String productId;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantity;
}
