package com.training.rest.services;


import com.training.rest.springrestproject.domain.Category;
import com.training.rest.springrestproject.repositories.CategoryRepository;
import com.training.rest.springrestproject.services.CategoryService;
import com.training.rest.springrestproject.services.CategoryServiceImpl;
import com.training.rest.springrestproject.v1.mapper.CategoryMapper;
import com.training.rest.springrestproject.v1.model.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private static final String NAME = "Joe";
    private static final Long ID = 1L;

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }


    @Test
    public void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        assertEquals(3, categoryDTOS.size());
    }

    @Test
    public void getCategoryByName() throws Exception {
        // given
        when(categoryRepository.findByName(anyString())).thenReturn(getCategory());

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        // then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }

    private Category getCategory() {
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);
        return category;
    }
}