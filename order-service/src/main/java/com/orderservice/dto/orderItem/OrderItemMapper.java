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

    public OrderItem orderItemRequestToOrderItem(OrderItemRequest orderItemRequest){
        return OrderItem.builder()
                .productId(orderItemRequest.getProductId())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

}
