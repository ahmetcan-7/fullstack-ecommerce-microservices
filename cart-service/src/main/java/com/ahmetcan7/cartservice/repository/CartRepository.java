package com.ahmetcan7.cartservice.repository;

import com.ahmetcan7.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long > {
    Optional<Cart> findCartByCustomerId(UUID customerId);
}
