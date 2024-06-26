package com.example.jdstreetwear.controller;

import com.example.jdstreetwear.model.Category;
import com.example.jdstreetwear.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Category category1 = new Category(1L, "Category1");
        Category category2 = new Category(2L, "Category2");
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        System.out.println("Sending request to get all categories");

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Category1"))
                .andExpect(jsonPath("$[1].name").value("Category2"))
                .andDo(result -> {
                    // Print the response JSON for debugging purposes
                    System.out.println(result.getResponse().getContentAsString());
                });

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        Category category = new Category(1L, "Category1");

        when(categoryService.getCategoryById(anyLong())).thenReturn(Optional.of(category));

        System.out.println("Sending request to get category by ID");

        mockMvc.perform(get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category1"))
                .andDo(result -> {
                    // Print the response JSON for debugging purposes
                    System.out.println(result.getResponse().getContentAsString());
                });

        verify(categoryService, times(1)).getCategoryById(anyLong());
    }

    @Test
    public void testGetCategoryByIdNotFound() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenReturn(Optional.empty());

        System.out.println("Sending request to get category by ID (not found)");

        mockMvc.perform(get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(anyLong());
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category(1L, "Category1");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        System.out.println("Sending request to create category: " + objectMapper.writeValueAsString(category));

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category1"))
                .andDo(result -> {
                    // Print the response JSON for debugging purposes
                    System.out.println(result.getResponse().getContentAsString());
                });

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Category category = new Category(1L, "UpdatedCategory");

        when(categoryService.updateCategory(anyLong(), any(Category.class))).thenReturn(category);

        System.out.println("Sending request to update category: " + objectMapper.writeValueAsString(category));

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedCategory"))
                .andDo(result -> {
                    // Print the response JSON for debugging purposes
                    System.out.println(result.getResponse().getContentAsString());
                });

        verify(categoryService, times(1)).updateCategory(anyLong(), any(Category.class));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyLong());

        System.out.println("Sending request to delete category");

        mockMvc.perform(delete("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }
}
