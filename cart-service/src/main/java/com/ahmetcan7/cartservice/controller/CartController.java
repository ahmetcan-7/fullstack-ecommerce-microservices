package com.ahmetcan7.cartservice.controller;

import com.ahmetcan7.cartservice.model.Cart;
import com.ahmetcan7.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> saveProductToCart(@RequestBody Cart cart){
        log.info("gelen cart{}",cart);
        return new ResponseEntity<>(cartService.save(cart), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.getCarts());
    }
}
