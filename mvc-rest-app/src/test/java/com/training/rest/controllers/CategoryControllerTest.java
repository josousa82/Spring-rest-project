package com.training.rest.controllers;


import com.training.rest.springrestproject.services.CategoryService;
import com.training.rest.springrestproject.services.exceptions.ResourceNotFoundException;
import com.training.rest.springrestproject.v1.controllers.CategoryController;
import com.training.rest.springrestproject.v1.controllers.exceptions.RestResponseEntityExceptionHandler;
import com.training.rest.springrestproject.v1.model.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void testListCategories() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(FRUITS_DTO, DRIED_DTO);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get(CATEGORY_API_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

    }

    @Test
    public void testGetCategoryByName() throws Exception {


        when(categoryService.getCategoryByName(anyString())).thenReturn(FRUITS_DTO);

        mockMvc.perform(get(CATEGORY_API_URL.concat("Fruits"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FRUITS_DTO.getName())));

    }

    @Test
    public void testGetCategoryByNameNotFound() throws Exception {
        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(CATEGORY_API_URL.concat("Fruits"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}