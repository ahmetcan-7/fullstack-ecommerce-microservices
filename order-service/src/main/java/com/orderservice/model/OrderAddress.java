package com.orderservice.model;

import com.ahmetcan7.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "orderAddresses")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderAddress  {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private String street;
    private String postalCode;
    private String city;

}
