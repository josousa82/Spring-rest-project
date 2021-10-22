package com.training.rest.springrestproject.services;


import com.training.rest.springrestproject.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByName(String name);
}
