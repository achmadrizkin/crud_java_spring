package com.example.demo.service;

import com.example.demo.dto.ResponseData;
import com.example.demo.helpers.Jwt;
import com.example.demo.model.entity.Product;
import com.example.demo.model.repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired // <-- this is like dependency injection
    private ProductRepo productRepo;

    @Autowired
    private Jwt jwt; // Inject Jwt bean here

    public ResponseEntity<ResponseData<Product>> save(String token, Product product) {
        ResponseData<Product> responseData = new ResponseData<>();

        if (token.isEmpty()) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("token cannot be null or must > 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        if (!jwt.validateToken(token)) {
            responseData.setStatusCode(401);
            responseData.setPayload(null);
            responseData.setMessage("token invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
        }

        if (product.getName().isEmpty() || product.getDescription().isEmpty() || product.getPrice() <= 0) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("name, description, and price cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        try {
            Product savedProduct = productRepo.save(product);
            responseData.setStatusCode(200);
            responseData.setPayload(savedProduct);
            responseData.setMessage("insert or update data success !");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } catch (DataAccessException e) {
            responseData.setStatusCode(500);
            responseData.setPayload(null);
            responseData.setMessage("Error occurred while insert or update data product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setPayload(null);
            responseData.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<ResponseData<Product>> findById(String token, Long id) {
        ResponseData<Product> responseData = new ResponseData<>();

        if (token.isEmpty()) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("token cannot be null or must > 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        if (!jwt.validateToken(token)) {
            responseData.setStatusCode(401);
            responseData.setPayload(null);
            responseData.setMessage("token invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
        }

        if (id == null || id <= 0) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("Id cannot be null or must > 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            responseData.setStatusCode(500);
            responseData.setPayload(null);
            responseData.setMessage("Internal Server Error: Data is empty !");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }

        responseData.setStatusCode(200);
        responseData.setPayload(product.get());
        responseData.setMessage("Get all data product is success !");
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    public ResponseEntity<ResponseData<Iterable<Product>>> findAll(String token) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();

        if (token.isEmpty()) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("token cannot be null or must > 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        if (!jwt.validateToken(token)) {
            responseData.setStatusCode(401);
            responseData.setPayload(null);
            responseData.setMessage("token invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
        }

        try {
            Iterable<Product> savedProduct = productRepo.findAll();
            responseData.setStatusCode(200);
            responseData.setPayload(savedProduct);
            responseData.setMessage("Get all data success !");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } catch (DataAccessException e) {
            responseData.setStatusCode(500);
            responseData.setPayload(null);
            responseData.setMessage("Error occurred while get all product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setPayload(null);
            responseData.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<ResponseData<Object>> deleteById(String token, Long id) {
        ResponseData<Object> responseData = new ResponseData<>();

        if (token.isEmpty()) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("token cannot be null or must > 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        if (!jwt.validateToken(token)) {
            responseData.setStatusCode(401);
            responseData.setPayload(null);
            responseData.setMessage("token invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
        }

        if (id == null || id <= 0) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("Id cannot be null or must be int");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            responseData.setStatusCode(500);
            responseData.setPayload(null);
            responseData.setMessage("Internal Server Error: Data is empty !");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }

        productRepo.deleteById(id);

        responseData.setStatusCode(200);
        responseData.setPayload(null);
        responseData.setMessage("Delete operation successful !");
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    public ResponseEntity<ResponseData<Iterable<Product>>> findByProductName(String token, String name) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();

        if (token.isEmpty()) {
            responseData.setStatusCode(400);
            responseData.setPayload(null);
            responseData.setMessage("token cannot be null or must > 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        if (!jwt.validateToken(token)) {
            responseData.setStatusCode(401);
            responseData.setPayload(null);
            responseData.setMessage("token invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
        }

        Iterable<Product> product = productRepo.findByProductName(name);
        responseData.setStatusCode(200);
        responseData.setPayload(product);
        responseData.setMessage("Get data product is success !");

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
