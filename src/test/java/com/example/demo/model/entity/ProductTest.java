package com.example.demo.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testGetterAndSetter() {
        // Create a Product instance
        Product product = new Product();

        // Set values using setter methods
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        // Test getter methods
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(10.5, product.getPrice());
    }

    @Test
    void testGetterAndSetterFAILED() {
        // Create a Product instance
        Product product = new Product();

        // Set values using setter methods
        product.setId(1L);
        product.setName("Test ProductFAIL");
        product.setDescription("Test DescriptionFAIL");
        product.setPrice(10.50);

        // Test getter methods
        assertNotEquals(1L, product.getId());
        assertNotEquals("Test Product", product.getName());
        assertNotEquals("Test Description", product.getDescription());
        assertNotEquals(10.5, product.getPrice());
    }
}
