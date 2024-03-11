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
import static org.mockito.ArgumentMatchers.eq;
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
        String token = "TOKEN-SCC";

        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        when(productService.save(eq(token), any(Product.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<ResponseData<Product>> responseEntity = productController.create(token, product);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testCreateFail_BadRequest() {
        Product product = new Product();
        String token = "TOKEN-SCC";

        product.setId(1L);
        product.setDescription("Test Description");
        product.setPrice(10.5);

        // Mocking behavior to return a ResponseEntity with HttpStatus.BAD_REQUEST
        when(productService.save(eq(token), any(Product.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));

        ResponseEntity<ResponseData<Product>> responseEntity = productController.create(token, product);

        // Asserting the status code of the ResponseEntity
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        String token = "TOKEN-SCC";

        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        when(productService.save(eq(token), any(Product.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<ResponseData<Product>> responseEntity = productController.update(token, product);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testFindById() {
        String token = "TOKEN-SCC";

        // Create a product with known ID
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.5);

        // Mock the service method to return the product when findById is called with ID 1
        when(productService.findById(token,1L)).thenReturn(
                ResponseEntity.ok(new ResponseData<>(200, product, "Get all data product is success !")));

        // Call the controller method with the known ID
        ResponseEntity<ResponseData<Product>> responseEntity = productController.findById(token, 1L);

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
        String token = "TOKEN-SCC";

        // Assuming your deleteById method returns a ResponseEntity
        ResponseEntity<ResponseData<Object>> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        // Stubbing the deleteById method of productService
        when(productService.deleteById(token, id)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseData<Object>> actualResponse = productController.delete(token, id);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testFindAll() {
        String token = "TOKEN-SCC";

        // Arrange
        Product product1 = new Product(1L, "Product 1", "Description 1", 300);
        Product product2 = new Product(2L, "Product 2", "Description 2", 150);
        List<Product> products = Arrays.asList(product1, product2);

        int expectedStatusCode = HttpStatus.OK.value();

        ResponseEntity<ResponseData<Iterable<Product>>> expectedResponse = new ResponseEntity<>(new ResponseData<>(expectedStatusCode, products, null), HttpStatus.OK);

        // Stubbing the findAll method of productService
        when(productService.findAll(token)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseData<Iterable<Product>>> actualResponse = productController.findAll(token);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testFindByProductName() {
        // Mock data
        String productName = "testProduct";
        String token = "TOKEN-SCC";

        // Arrange
        Product product1 = new Product(1L, "Product 1", "Description 1", 300);
        Product product2 = new Product(2L, "Product 2", "Description 2", 150);
        List<Product> productList = Arrays.asList(product1, product2);

        ResponseData<Object> responseData = new ResponseData<>();
        responseData.setStatusCode(200);
        responseData.setPayload(productList);
        responseData.setMessage("Get data product is success !");

        int expectedStatusCode = HttpStatus.OK.value();

        ResponseEntity<ResponseData<Iterable<Product>>> expectedResponse = new ResponseEntity<>(new ResponseData<>(expectedStatusCode, productList, null), HttpStatus.OK);

        // Stubbing the findAll method of productService
        when(productService.findByProductName(token, productName)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseData<Iterable<Product>>> actualResponse = productController.findByProductName(token, productName);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }
}
