package com.ahmetcan7.productservice.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class UpdateProductRequest {
    @NotNull
    private String name;
    @NotNull
    private BigDecimal unitPrice;
    @NotNull
    private String description;
//    @NotNull
//    private Integer quantityInStock;
    @NotNull
    private Long categoryId;
    @NotNull
    private String imageUrl;
}
