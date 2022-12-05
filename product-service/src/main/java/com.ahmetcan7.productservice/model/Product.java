package com.ahmetcan7.productservice.model;


import com.ahmetcan7.common.model.AdvanceBaseModal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "products")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;
    private BigDecimal unitPrice;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;




    // todo: created at,updated at ,delted at,createdby,updatedby,,deletedby, soft delete
}
