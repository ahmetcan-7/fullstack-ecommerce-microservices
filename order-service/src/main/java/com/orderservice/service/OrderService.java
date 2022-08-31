package com.orderservice.service;

import com.ahmetcan7.common.exception.DomainException;
import com.orderservice.dto.order.OrderDto;
import com.orderservice.dto.order.OrderMapper;
import com.orderservice.dto.order.OrderRequest;
import com.orderservice.dto.orderItem.OrderItemRequest;
import com.orderservice.model.Order;
import com.orderservice.model.OrderItem;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    public OrderDto createOrder(OrderRequest orderRequest){

        Order order = orderMapper.orderRequestToOrder(orderRequest,getTotalPrice(orderRequest));
        order.getAddress().setOrder(order);
        order.getItems().forEach(item -> item.setOrder(order));

        orderRepository.save(order);

        if(order.getId() == null){
            log.error("Order could not be created with id: {}",order.getId());
            throw new DomainException("Order could not be created with id: "+
                    order.getId());
        }

        log.info("Order is created with id: {}", order.getId());

        return orderMapper.orderToOrderDto(order);
    }

    private BigDecimal getTotalPrice(OrderRequest orderRequest) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
            BigDecimal subtotal = orderItemRequest.getPrice()
                    .multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()));

            totalPrice = totalPrice.add(subtotal);
        }

        return totalPrice;
    }
}
