package com.ahmetcan7.productservice.dto.product;

import com.ahmetcan7.productservice.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String id;
    private String name;
    private BigDecimal price;
    private CategoryDto category;
    private String description;
}
