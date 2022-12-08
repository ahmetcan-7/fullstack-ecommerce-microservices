package com.ahmetcan7.productservice.repository;

import com.ahmetcan7.productservice.model.ProductModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface ProductElasticRepository extends ElasticsearchRepository<ProductModel, UUID> {
}
