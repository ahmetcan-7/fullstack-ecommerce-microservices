package com.ahmetcan7.productservice.dto.category;

import com.ahmetcan7.productservice.model.Category;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper {
    public CategoryDto categoryToCategoryDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
