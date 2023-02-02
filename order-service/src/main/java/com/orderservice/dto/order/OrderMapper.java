package com.orderservice.dto.order;

import com.orderservice.dto.orderAddress.OrderAddressMapper;
import com.orderservice.dto.orderItem.OrderItemMapper;
import com.orderservice.model.Order;
import com.orderservice.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderAddressMapper orderAddressMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderDto orderToOrderDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .address(orderAddressMapper.orderAddressToOrderAddressDto(order.getAddress()))
                .items(order.getItems()
                        .stream()
                        .map(orderItemMapper::orderToOrderItemDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Order orderRequestToOrder(CreateOrderRequest createOrderRequest){
        return Order.builder()
                .customerId(UUID.fromString((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orderStatus(OrderStatus.PENDING)
                .address(orderAddressMapper.orderAddressRequestToOrderAddress(createOrderRequest.getAddress()))
                .items(createOrderRequest.getItems()
                        .stream()
                        .map(orderItemMapper::orderItemRequestToOrderItem)
                        .collect(Collectors.toList()))
                .build();
    }

}
