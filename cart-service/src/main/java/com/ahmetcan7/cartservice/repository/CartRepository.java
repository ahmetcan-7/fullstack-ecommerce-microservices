package com.ahmetcan7.cartservice.repository;

import com.ahmetcan7.cartservice.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart,Long> {
    List<Cart> findAll();
}
