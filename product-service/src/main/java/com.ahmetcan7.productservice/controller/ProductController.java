package com.ahmetcan7.productservice.controller;


import com.ahmetcan7.productservice.dto.product.ProductDto;
import com.ahmetcan7.productservice.dto.product.ProductRequest;
import com.ahmetcan7.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest),HttpStatus.CREATED);
    }

}
