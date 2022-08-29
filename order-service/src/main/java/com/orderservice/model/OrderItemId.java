package com.orderservice.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {

    private Long id;
    private Order order;

}
