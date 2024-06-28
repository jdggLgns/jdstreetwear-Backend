package com.example.jdstreetwear.controller;

import com.example.jdstreetwear.model.Discount;
import com.example.jdstreetwear.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiscountController.class)
class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountService discountService;

    private Discount discount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        discount = new Discount();
        discount.setId(1L);
        discount.setName("Summer Sale");
        discount.setPercentage(10.0);
        discount.setState("active");
    }

    @Test
    @WithMockUser
    void testGetAllDiscounts() throws Exception {
        when(discountService.getAllDiscounts()).thenReturn(Arrays.asList(discount));

        mockMvc.perform(get("/api/discounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Summer Sale"))
                .andExpect(jsonPath("$[0].percentage").value(10.0))
                .andExpect(jsonPath("$[0].state").value("active"));
    }

    @Test
    @WithMockUser
    void testGetDiscountById() throws Exception {
        when(discountService.getDiscountById(1L)).thenReturn(Optional.of(discount));

        mockMvc.perform(get("/api/discounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Summer Sale"))
                .andExpect(jsonPath("$.percentage").value(10.0))
                .andExpect(jsonPath("$.state").value("active"));
    }



}
