package com.ahmetcan7.productservice.dto.product;


import com.ahmetcan7.productservice.dto.category.CategoryMapper;
import com.ahmetcan7.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public ProductDto productToProductDto(Product product){
        return ProductDto.builder()
                .name(product.getName())
                .id(product.getId())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(categoryMapper.categoryToCategoryDto(product.getCategory()))
                .build();
    }

}
