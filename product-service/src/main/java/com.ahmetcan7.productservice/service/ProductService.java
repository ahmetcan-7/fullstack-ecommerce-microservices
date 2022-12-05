package com.ahmetcan7.productservice.service;

import com.ahmetcan7.amqp.RabbitMQMessageProducer;
import com.ahmetcan7.amqp.InventoryRequest;
import com.ahmetcan7.productservice.dto.product.ProductDto;
import com.ahmetcan7.productservice.dto.product.ProductMapper;
import com.ahmetcan7.productservice.dto.product.CreateProductRequest;
import com.ahmetcan7.productservice.dto.product.UpdateProductRequest;
import com.ahmetcan7.productservice.exception.ProductNotFoundException;
import com.ahmetcan7.productservice.model.Category;
import com.ahmetcan7.productservice.model.Product;
import com.ahmetcan7.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::productToProductDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) {
        return productMapper.productToProductDto(productRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Product with id: {} could not be found!", id);
                    throw new ProductNotFoundException("Product with id " + id + " could not be found!");
                }));
    }

    public ProductDto createProduct(CreateProductRequest createProductRequest) {

        Category category = categoryService.getCategoryById(createProductRequest.getCategoryId());

        Product product =  Product.builder()
                .name(createProductRequest.getName())
                .unitPrice(createProductRequest.getUnitPrice())
                .description(createProductRequest.getDescription())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);

        InventoryRequest inventoryRequest = new InventoryRequest(savedProduct.getId(),createProductRequest.getQuantityInStock());
        rabbitMQMessageProducer.publish(
                inventoryRequest,
                "inventory.exchange",
                "internal.inventory.routing-key"
        );

        return productMapper.productToProductDto(savedProduct);
    }

    public ProductDto updateProduct(UpdateProductRequest updateProductRequest,UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->{
                    log.error("Product with id: {} could not be found!", productId);
                    throw new ProductNotFoundException("Product with id " + productId + " could not be found!");
                });

        Category category = categoryService.getCategoryById(updateProductRequest.getCategoryId());

        product.setCategory(category);
        product.setDescription(updateProductRequest.getDescription());
        product.setName(updateProductRequest.getName());
        product.setUnitPrice(updateProductRequest.getUnitPrice());

        // todo:inventoryde quantityi guncelle -> circuit breaker kullanilabilir

        return productMapper.productToProductDto(product);
    }

    public UUID deleteProduct(UUID id) {
        productRepository.deleteById(id);
        // todo: urunu inventory servistende kaldir.
        return id;
    }
}


