package com.ahmetcan7.productservice.service;

import com.ahmetcan7.amqp.RabbitMQMessageProducer;
import com.ahmetcan7.amqp.InventoryRequest;
import com.ahmetcan7.productservice.dto.product.*;
import com.ahmetcan7.productservice.enumeration.Sort;
import com.ahmetcan7.productservice.exception.ProductNotFoundException;
import com.ahmetcan7.productservice.model.Category;
import com.ahmetcan7.productservice.model.Product;
import com.ahmetcan7.productservice.model.ProductModel;
import com.ahmetcan7.productservice.repository.ProductElasticRepository;
import com.ahmetcan7.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;

import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final ProductElasticRepository productElasticRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final ElasticsearchOperations elasticsearchOperations;

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

    @Transactional
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

        saveProductToElastic(savedProduct);

        return productMapper.productToProductDto(savedProduct);
    }

    private void saveProductToElastic(Product product) {
        ProductModel productModel = ProductModel.builder()
                .categoryName(product.getCategory().getName())
                .description(product.getDescription())
                .id(product.getId())
                .name(product.getName())
                .unitPrice(product.getUnitPrice())
                .build();

        productElasticRepository.save(productModel);
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

        // todo:inventoryde quantityi guncelle
        // todo:elastic update

        return productMapper.productToProductDto(product);
    }

    @Transactional
    public UUID deleteProduct(UUID id) {
        productRepository.deleteById(id);
        productElasticRepository.deleteById(id);
        // todo: urunu inventory servistende kaldir.
        return id;
    }

    public List<ProductSearchDto> searchProduct(String searchTerm, int page, int size, Sort sort) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSorts(SortBuilders.fieldSort(sort.getField()).order(sort.getOrder()))
                .withPageable(PageRequest.of(page, size))
                .withQuery(multiMatchQuery(searchTerm)
                        .field("name")
                        .field("categoryName")
                        .field("description")
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.AUTO)
                        .prefixLength(3)
                ).build();

        List<SearchHit<ProductModel>> productModels = elasticsearchOperations.search(searchQuery, ProductModel.class,
                IndexCoordinates.of("product")).getSearchHits();

        return productModels.stream().map(productMapper::productSearchDtoMapper).collect(Collectors.toList());
    }

}


