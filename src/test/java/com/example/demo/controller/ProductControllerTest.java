package com.example.demo.controller;

import com.example.demo.dto.ResponseData;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testCreate() {
        Product product = new Product();

        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        when(productService.save(any(Product.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<ResponseData<Product>> responseEntity = productController.create(product);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testCreateFail_BadRequest() {
        Product product = new Product();

        product.setId(1L);
        product.setDescription("Test Description");
        product.setPrice(10.5);

        when(productService.save(any(Product.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        ResponseEntity<ResponseData<Product>> responseEntity = productController.create(product);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate() {
        Product product = new Product();

        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        when(productService.save(any(Product.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<ResponseData<Product>> responseEntity = productController.update(product);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testFindById() {
        // Create a product with known ID
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        // Mock the service method to return the product when findById is called with ID 1
        when(productService.findById(1L)).thenReturn(
                ResponseEntity.ok(new ResponseData<>(200, product, "Get all data product is success !")));

        // Call the controller method with the known ID
        ResponseEntity<ResponseData<Product>> responseEntity = productController.findById(1L);

        // Verify that the response status is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that the returned product matches the expected product
        assertEquals(product.getId(), responseEntity.getBody().getPayload().getId());
        assertEquals(product.getName(), responseEntity.getBody().getPayload().getName());
        assertEquals(product.getDescription(), responseEntity.getBody().getPayload().getDescription());
        assertEquals(product.getPrice(), responseEntity.getBody().getPayload().getPrice());
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long id = 123L;
        // Assuming your deleteById method returns a ResponseEntity
        ResponseEntity<ResponseData<Object>> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        // Stubbing the deleteById method of productService
        when(productService.deleteById(id)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseData<Object>> actualResponse = productController.delete(id);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testFindAll() {
        // Arrange
        Product product1 = new Product(1L, "Product 1", "Description 1", 300);
        Product product2 = new Product(2L, "Product 2", "Description 2", 150);
        List<Product> products = Arrays.asList(product1, product2);

        int expectedStatusCode = HttpStatus.OK.value();

        ResponseEntity<ResponseData<Iterable<Product>>> expectedResponse = new ResponseEntity<>(new ResponseData<>(expectedStatusCode, products, null), HttpStatus.OK);

        // Stubbing the findAll method of productService
        when(productService.findAll()).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseData<Iterable<Product>>> actualResponse = productController.findAll();

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

}
