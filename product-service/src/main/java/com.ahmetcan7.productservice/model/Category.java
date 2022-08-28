package com.ahmetcan7.productservice.model;

import com.ahmetcan7.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity(name = "categories")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Category extends BaseModel {
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
