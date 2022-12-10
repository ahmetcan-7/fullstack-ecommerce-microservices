package com.ahmetcan7.productservice.controller;


import com.ahmetcan7.productservice.dto.product.ProductDto;
import com.ahmetcan7.productservice.dto.product.CreateProductRequest;
import com.ahmetcan7.productservice.dto.product.ProductSearchDto;
import com.ahmetcan7.productservice.dto.product.UpdateProductRequest;
import com.ahmetcan7.productservice.enumeration.Sort;
import com.ahmetcan7.productservice.model.Product;
import com.ahmetcan7.productservice.model.ProductModel;
import com.ahmetcan7.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(productService.createProduct(createProductRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest,
                                                    @PathVariable UUID id){
        return ResponseEntity.ok(productService.updateProduct(updateProductRequest,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteProduct(@PathVariable UUID id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping
    public List<ProductSearchDto> getProductBySearch(@RequestParam(required = false, defaultValue = "") String searchTerm,
                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false, defaultValue = "dateAsc") Sort sort,
                                                     @RequestParam(required = false, defaultValue = "") String filter){
        return productService.searchProduct(searchTerm,page,size,sort,filter);
    }

}
