package com.ahmetcan7.productservice.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ProductSearchDto{
    private UUID id;
    private String name;
    private BigDecimal unitPrice;
    private String categoryName;
    private String description;
    private LocalDate createdDate;
    private String imageUrl;
}
