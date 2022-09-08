package com.ahmetcan7.cartservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@RedisHash("cartItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID productId;
    private String name;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private Integer quantity;
}
