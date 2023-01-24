package com.ahmetcan7.productservice.controller;

import com.ahmetcan7.productservice.dto.category.CategoryDto;
import com.ahmetcan7.productservice.dto.category.CreateCategoryRequest;
import com.ahmetcan7.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(createCategoryRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
