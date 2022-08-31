package com.orderservice.model;

import com.ahmetcan7.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "orderItems")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderItem extends BaseModel  {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productId;
    private BigDecimal price;
    private Integer quantity;

}