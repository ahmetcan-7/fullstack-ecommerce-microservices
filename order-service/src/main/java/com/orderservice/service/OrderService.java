package com.orderservice.service;

import com.orderservice.client.InventoryServiceClient;
import com.orderservice.dto.inventory.InventoryCheckRequest;
import com.orderservice.dto.inventory.InventoryCheckResponse;
import com.orderservice.dto.order.OrderDto;
import com.orderservice.dto.order.OrderMapper;
import com.orderservice.dto.order.CreateOrderRequest;
import com.orderservice.dto.orderItem.CreateOrderItemRequest;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final InventoryServiceClient inventoryServiceClient;
    public OrderDto createOrder(CreateOrderRequest createOrderRequest){

        Order order = orderMapper.orderRequestToOrder(createOrderRequest,getTotalPrice(createOrderRequest));
        order.getAddress().setOrder(order);
        order.getItems().forEach(item -> item.setOrder(order));

        List<InventoryCheckRequest> inventoryCheckRequests = order.getItems().stream()
                .map(item -> new InventoryCheckRequest(item.getProductId(),item.getQuantity()))
                .collect(Collectors.toList());

        InventoryCheckResponse inventoryCheckResponse = inventoryServiceClient.isInStock(inventoryCheckRequests);

        if(!inventoryCheckResponse.getIsInStock()){
          throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
        // TODO:urun siparis olusunca stoktan azalt
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
