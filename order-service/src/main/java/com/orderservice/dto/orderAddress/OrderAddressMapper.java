package com.orderservice.dto.orderAddress;

import com.orderservice.model.OrderAddress;
import org.springframework.stereotype.Component;

@Component
public class OrderAddressMapper {

    public OrderAddressDto orderAddressToOrderAddressDto(OrderAddress orderAddress){
        return OrderAddressDto.builder()
                .street(orderAddress.getStreet())
                .postalCode(orderAddress.getPostalCode())
                .city(orderAddress.getCity())
                .build();
    }

    public OrderAddress orderAddressRequestToOrderAddress(OrderAddressRequest orderAddressRequest){
        return OrderAddress.builder()
                .street(orderAddressRequest.getStreet())
                .postalCode(orderAddressRequest.getPostalCode())
                .city(orderAddressRequest.getCity())
                .build();
    }
}

