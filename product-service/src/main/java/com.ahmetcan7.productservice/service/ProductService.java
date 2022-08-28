package com.ahmetcan7.productservice.service;

import com.ahmetcan7.common.exception.DomainException;
import com.ahmetcan7.common.exception.NotFoundException;
import com.ahmetcan7.productservice.dto.product.ProductDto;
import com.ahmetcan7.productservice.dto.product.ProductMapper;
import com.ahmetcan7.productservice.dto.product.ProductRequest;
import com.ahmetcan7.productservice.model.Category;
import com.ahmetcan7.productservice.model.Product;
import com.ahmetcan7.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::productToProductDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            log.error("Product with id: {} could not be found!", id);
            throw new NotFoundException("Product with id " + id + " could not be found!");
        }

        return productMapper.productToProductDto(product.get());
    }

    public ProductDto createProduct(ProductRequest productRequest) {

        Category category = categoryService.getCategoryById(productRequest.getCategoryId());

        Product product =  Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        if(savedProduct.getId() == null){
            log.error("Product could not be created with id: {}",savedProduct.getId());
            throw new DomainException("Product could not be created with id: "+
                    savedProduct.getId());
        }

        log.info("Product is created with id: {}", savedProduct.getId());

        return productMapper.productToProductDto(savedProduct);
    }
}


