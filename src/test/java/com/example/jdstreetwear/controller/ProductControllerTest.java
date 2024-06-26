package com.example.jdstreetwear.controller;

import com.example.jdstreetwear.model.Product;
import com.example.jdstreetwear.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setColor("Red");
        product.setProductCode("TP001");
        product.setSize("M");
        product.setPrice(99.99);
        product.setType("T-shirt");
    }

    @Test
    @WithMockUser
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[0].productCode").value("TP001"))
                .andExpect(jsonPath("$[0].size").value("M"))
                .andExpect(jsonPath("$[0].price").value(99.99))
                .andExpect(jsonPath("$[0].type").value("T-shirt"));
    }

    @Test
    @WithMockUser
    public void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.productCode").value("TP001"))
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andExpect(jsonPath("$.type").value("T-shirt"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Product\", \"color\":\"Red\", \"productCode\":\"TP001\", \"size\":\"M\", \"price\":99.99, \"type\":\"T-shirt\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.productCode").value("TP001"))
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andExpect(jsonPath("$.type").value("T-shirt"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\", \"color\":\"Blue\", \"productCode\":\"UP001\", \"size\":\"L\", \"price\":109.99, \"type\":\"Sweatshirt\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.productCode").value("TP001"))
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andExpect(jsonPath("$.type").value("T-shirt"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1L);
    }
}
