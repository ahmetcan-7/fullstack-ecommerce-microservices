package com.orderservice.service;

import com.orderservice.dto.order.OrderDto;
import com.orderservice.dto.order.OrderMapper;
import com.orderservice.dto.order.CreateOrderRequest;
import com.orderservice.dto.orderItem.CreateOrderItemRequest;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    public OrderDto createOrder(CreateOrderRequest createOrderRequest){

        Order order = orderMapper.orderRequestToOrder(createOrderRequest,getTotalPrice(createOrderRequest));
        order.getAddress().setOrder(order);
        order.getItems().forEach(item -> item.setOrder(order));

        return orderMapper.orderToOrderDto(orderRepository.save(order));
    }

    private BigDecimal getTotalPrice(CreateOrderRequest createOrderRequest) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CreateOrderItemRequest createOrderItemRequest : createOrderRequest.getItems()) {
            BigDecimal subtotal = createOrderItemRequest.getPrice()
                    .multiply(BigDecimal.valueOf(createOrderItemRequest.getQuantity()));

            totalPrice = totalPrice.add(subtotal);
        }

        return totalPrice;
    }
}
