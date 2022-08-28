package com.ahmetcan7.productservice.dto.category;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CategoryRequest {
    @NotNull
    private String name;
}
