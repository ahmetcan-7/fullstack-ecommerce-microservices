package com.ahmetcan7.productservice.repository;

import com.ahmetcan7.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
