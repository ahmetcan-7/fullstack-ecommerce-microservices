package com.ahmetcan7.productservice.dto.category;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateCategoryRequest {
    @NotNull
    private String name;
}
