package com.ahmetcan7.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product")
@Builder
public class ProductModel implements Serializable {
    @Id
    private UUID id;
    private String name;
    private BigDecimal unitPrice;
    private String description;
    private String categoryName;
}
