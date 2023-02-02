package com.orderservice.dto.order;

import com.orderservice.dto.orderAddress.CreateOrderAddressRequest;
import com.orderservice.dto.orderItem.CreateOrderItemRequest;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
public class CreateOrderRequest {
    @NotNull
    private CreateOrderAddressRequest address;
    @NotNull
    private List<CreateOrderItemRequest> items;
}
