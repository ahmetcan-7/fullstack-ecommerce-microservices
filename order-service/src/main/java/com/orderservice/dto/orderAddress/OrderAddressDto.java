package com.orderservice.dto.orderAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderAddressDto {
    private String street;
    private String postalCode;
    private String city;
}
