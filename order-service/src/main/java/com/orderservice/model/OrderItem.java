package com.orderservice.model;

import com.ahmetcan7.common.model.BaseModel;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@IdClass(OrderItemId.class)
@Entity(name = "orderItems")
public class OrderItem extends BaseModel {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productId;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subTotal;

}