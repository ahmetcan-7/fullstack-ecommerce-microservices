package com.ahmetcan7.productservice.controller;

import com.ahmetcan7.productservice.dto.category.CategoryDto;
import com.ahmetcan7.productservice.dto.category.CreateCategoryRequest;
import com.ahmetcan7.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(createCategoryRequest), HttpStatus.CREATED);
    }
}
