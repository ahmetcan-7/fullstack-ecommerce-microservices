package com.ahmetcan7.cartservice.service;

import com.ahmetcan7.cartservice.dto.cartItem.CartItemDto;
import com.ahmetcan7.cartservice.dto.cartItem.CartItemMapper;
import com.ahmetcan7.cartservice.dto.cartItem.CreateCartItemRequest;
import com.ahmetcan7.cartservice.model.Cart;
import com.ahmetcan7.cartservice.model.CartItem;
import com.ahmetcan7.cartservice.repository.CartRepository;
import com.ahmetcan7.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;

    private final CartItemMapper cartItemMapper;
    public void save(UUID customerId, CreateCartItemRequest createCartItemRequest) {
        CartItem cartItem = cartItemMapper.createCartItemRequestToCartItem(createCartItemRequest);

        Optional<Cart> cart = cartRepository.findCartByCustomerId(customerId);

        if(cart.isPresent()) {
            cart.get().getCartItems().add(cartItem);
            log.info("cart bu brom"+cart);
            cartRepository.save(cart.get());
        }else{
            log.info("buraya geldi la"+cart);
            Cart newCart = Cart.builder()
                    .customerId(customerId)
                    .cartItems(List.of(cartItem))
                    .build();
            cartRepository.save(newCart);
        }

    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }
}
