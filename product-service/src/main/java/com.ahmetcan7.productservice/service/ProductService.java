package com.ahmetcan7.productservice.service;

import com.ahmetcan7.common.exception.NotFoundException;
import com.ahmetcan7.productservice.dto.InventoryDto;
import com.ahmetcan7.productservice.dto.product.ProductDto;
import com.ahmetcan7.productservice.dto.product.ProductMapper;
import com.ahmetcan7.productservice.dto.product.CreateProductRequest;
import com.ahmetcan7.productservice.model.Category;
import com.ahmetcan7.productservice.model.Product;
import com.ahmetcan7.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    private final WebClient.Builder webClientBuilder;
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::productToProductDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) {
        return productMapper.productToProductDto(productRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Product with id: {} could not be found!", id);
                    throw new NotFoundException("Product with id " + id + " could not be found!");
                }));
    }

    @Transactional
    public ProductDto createProduct(CreateProductRequest createProductRequest) {

        Category category = categoryService.getCategoryById(createProductRequest.getCategoryId());

        Product product =  Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .description(createProductRequest.getDescription())
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        InventoryDto inventoryDto = new InventoryDto(savedProduct.getId(),createProductRequest.getQuantity());

        String response = webClientBuilder.build()
                .post()
                .uri("http://localhost:8082/v1/inventories")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(inventoryDto), InventoryDto.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return productMapper.productToProductDto(savedProduct);
    }
}


