package com.ahmetcan7.productservice.dto.product;


import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class ProductRequest {
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long categoryId;
    private String description;
}
