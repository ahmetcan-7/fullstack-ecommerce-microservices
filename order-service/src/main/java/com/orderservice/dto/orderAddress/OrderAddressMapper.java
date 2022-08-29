package com.orderservice.dto.orderAddress;

import com.orderservice.dto.order.OrderDto;
import com.orderservice.model.Order;
import com.orderservice.model.OrderAddress;
import com.orderservice.model.OrderStatus;
import lombok.RequiredArgsConstructor;
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

