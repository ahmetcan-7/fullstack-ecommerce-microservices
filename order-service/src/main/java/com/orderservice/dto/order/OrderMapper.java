package com.orderservice.dto.order;

import com.orderservice.dto.orderAddress.OrderAddressMapper;
import com.orderservice.dto.orderItem.OrderItemMapper;
import com.orderservice.model.Order;
import com.orderservice.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
                .price(order.getPrice())
                .orderStatus(order.getOrderStatus())
                .address(orderAddressMapper.orderAddressToOrderAddressDto(order.getAddress()))
                .items(order.getItems()
                        .stream()
                        .map(orderItemMapper::orderToOrderItemDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Order orderRequestToOrder(OrderRequest orderRequest, BigDecimal totalPrice){
        return Order.builder()
                .customerId(orderRequest.getCustomerId())
                .price(totalPrice)
                .orderStatus(OrderStatus.PENDING)
                .address(orderAddressMapper.orderAddressRequestToOrderAddress(orderRequest.getAddress()))
                .items(orderRequest.getItems()
                        .stream()
                        .map(orderItemMapper::orderItemRequestToOrderItem)
                        .collect(Collectors.toList()))
                .build();
    }

}
