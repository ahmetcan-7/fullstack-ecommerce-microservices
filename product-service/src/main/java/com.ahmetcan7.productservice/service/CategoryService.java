package com.ahmetcan7.productservice.service;

import com.ahmetcan7.common.exception.DomainException;
import com.ahmetcan7.common.exception.NotFoundException;
import com.ahmetcan7.productservice.dto.category.CategoryDto;
import com.ahmetcan7.productservice.dto.category.CategoryMapper;
import com.ahmetcan7.productservice.dto.category.CategoryRequest;
import com.ahmetcan7.productservice.model.Category;
import com.ahmetcan7.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public Category getCategoryById(Long id){
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()){
            log.error("Category with id: {} could not be found!",id);
            throw new NotFoundException("Category with id " + id + "could not be found!");
        }

        return category.get();
    }

    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();

        Category savedCategory = categoryRepository.save(category);

        if(category.getId() == null){
            log.error("Category could not be created with id: {}",savedCategory.getId());
            throw new DomainException("Category could not be created with id: "+
                    savedCategory.getId());
        }

        log.info("Category is created with id: {}", savedCategory.getId());

        return categoryMapper.categoryToCategoryDto(category);
    }
}
