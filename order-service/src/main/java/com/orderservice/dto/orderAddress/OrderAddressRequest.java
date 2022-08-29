package com.orderservice.dto.orderAddress;

import lombok.*;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderAddressRequest {
    @NotNull
    private String street;
    @NotNull
    private String postalCode;
    @NotNull
    private String city;
}