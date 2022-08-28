package com.ahmetcan7.productservice.repository;

import com.ahmetcan7.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, String> {

}
