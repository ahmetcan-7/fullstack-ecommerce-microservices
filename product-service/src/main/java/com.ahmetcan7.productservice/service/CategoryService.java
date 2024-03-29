package com.ahmetcan7.productservice.service;

import com.ahmetcan7.productservice.dto.category.CategoryDto;
import com.ahmetcan7.productservice.dto.category.CategoryMapper;
import com.ahmetcan7.productservice.dto.category.CreateCategoryRequest;
import com.ahmetcan7.productservice.exception.CategoryNotFoundException;
import com.ahmetcan7.productservice.model.Category;
import com.ahmetcan7.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Category with id: {} could not be found!",id);
                    throw new CategoryNotFoundException("Category with id " + id + "could not be found!");
                });
    }

    public CategoryDto createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .build();

        return categoryMapper.categoryToCategoryDto(categoryRepository.save(category));
    }

    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }
}
