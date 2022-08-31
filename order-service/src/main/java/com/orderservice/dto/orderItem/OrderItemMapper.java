package com.orderservice.dto.orderItem;

import com.orderservice.model.OrderItem;
import org.springframework.stereotype.Component;


@Component
public class OrderItemMapper {

    public OrderItemDto orderToOrderItemDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .productId(orderItem.getProductId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItem orderItemRequestToOrderItem(CreateOrderItemRequest createOrderItemRequest){
        return OrderItem.builder()
                .productId(createOrderItemRequest.getProductId())
                .price(createOrderItemRequest.getPrice())
                .quantity(createOrderItemRequest.getQuantity())
                .build();
    }

}
