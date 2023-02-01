package com.ahmetcan7.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product")
@Builder
@Setting(settingPath = "/es-settings.json")
public class ProductModel implements Serializable {
    @Id
    private UUID id;
    private String name;
    private BigDecimal unitPrice;
    private String description;
    private String categoryName;
    @Field(type=FieldType.Date, format={}, pattern="yyyy-MM-dd")
    private LocalDate createdDate;
    private String imageUrl;
}
