package com.ahmetcan7.productservice.repository;

import com.ahmetcan7.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByIdIn(List<UUID> ids);
}
