package com.ahmetcan7.productservice.repository;

import com.ahmetcan7.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;


public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {
}
