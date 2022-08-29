package com.orderservice.repository;

import com.orderservice.model.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<OrderAddress,String> {
}
