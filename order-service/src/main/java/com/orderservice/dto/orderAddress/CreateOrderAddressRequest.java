package com.orderservice.dto.orderAddress;

import lombok.*;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateOrderAddressRequest {
    @NotNull
    private String city;
    @NotNull
    private String district;
    @NotNull
    private String addressDetail;
}