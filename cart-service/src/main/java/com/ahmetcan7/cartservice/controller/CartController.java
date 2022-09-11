package com.ahmetcan7.cartservice.controller;

import com.ahmetcan7.cartservice.dto.cartItem.CartItemDto;
import com.ahmetcan7.cartservice.dto.cartItem.CreateCartItemRequest;
import com.ahmetcan7.cartservice.model.Cart;
import com.ahmetcan7.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addProductToCart(@RequestParam UUID customerId,
                                                        @RequestBody CreateCartItemRequest createCartItemRequest){

        cartService.save(customerId,createCartItemRequest);
        return new ResponseEntity<>("Product is added", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.getCarts());
    }
}
