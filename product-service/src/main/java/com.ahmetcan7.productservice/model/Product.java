package com.ahmetcan7.productservice.model;


import com.ahmetcan7.common.model.AdvanceBaseModal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "products")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Product extends AdvanceBaseModal {
    private String name;
    private BigDecimal price;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
