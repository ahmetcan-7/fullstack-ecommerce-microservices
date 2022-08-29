package com.orderservice.model;

import com.ahmetcan7.common.model.AdvanceBaseModal;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "orders")
public class Order extends AdvanceBaseModal {
    private String customerId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddress address;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;
}
