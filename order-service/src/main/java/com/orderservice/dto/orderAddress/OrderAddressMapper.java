package com.orderservice.dto.orderAddress;

import com.orderservice.model.OrderAddress;
import org.springframework.stereotype.Component;

@Component
public class OrderAddressMapper {

    public OrderAddressDto orderAddressToOrderAddressDto(OrderAddress orderAddress){
        return OrderAddressDto.builder()
                .city(orderAddress.getCity())
                .addressDetail(orderAddress.getAddressDetail())
                .district(orderAddress.getDistrict())
                .build();
    }

    public OrderAddress orderAddressRequestToOrderAddress(CreateOrderAddressRequest createOrderAddressRequest){
        return OrderAddress.builder()
                .city(createOrderAddressRequest.getCity())
                .addressDetail(createOrderAddressRequest.getAddressDetail())
                .district(createOrderAddressRequest.getDistrict())
                .build();
    }
}

