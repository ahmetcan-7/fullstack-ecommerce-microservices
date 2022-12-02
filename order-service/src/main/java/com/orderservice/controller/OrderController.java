package com.orderservice.controller;

import com.orderservice.dto.order.OrderDto;
import com.orderservice.dto.order.CreateOrderRequest;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest
            ,@RequestHeader(value="X-auth-user-id") String denemeHeader){

        return new ResponseEntity<>(orderService.createOrder(createOrderRequest),HttpStatus.CREATED);
    }
}
