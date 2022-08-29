package com.orderservice.dto.order;

import com.orderservice.dto.orderAddress.OrderAddressRequest;
import com.orderservice.dto.orderItem.OrderItemRequest;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class OrderRequest {
    @NotNull
    private String customerId;
    @NotNull
    private OrderAddressRequest address;
    @NotNull
    private List<OrderItemRequest> items;
}
