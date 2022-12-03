package com.orderservice.controller;

import com.orderservice.dto.order.OrderDto;
import com.orderservice.dto.order.CreateOrderRequest;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    // @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest){

        return new ResponseEntity<>(orderService.createOrder(createOrderRequest),HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<String> getOrder(){
//        return ResponseEntity.ok("adam geldi");
//    }
}
