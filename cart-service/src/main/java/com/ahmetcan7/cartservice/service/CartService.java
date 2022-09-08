package com.ahmetcan7.cartservice.service;

import com.ahmetcan7.cartservice.model.Cart;
import com.ahmetcan7.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @CacheEvict(value = "carts",allEntries = true)
    public Cart save(Cart cart) {
       return cartRepository.save(cart);
    }

    @Cacheable("carts")
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }
}
