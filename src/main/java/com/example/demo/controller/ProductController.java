package com.example.demo.controller;

import com.example.demo.dto.ResponseData;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(
            @RequestHeader("token") String token,
            @RequestBody Product product) {
        return productService.save(token, product);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Product>>> findAll(
            @RequestHeader("token") String token) {
        return productService.findAll(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> findById(
            @RequestHeader("token") String token,
            @PathVariable("id") Long id) {
        return productService.findById(token, id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByProductName(
            @RequestHeader("token") String token,
            @PathVariable("name") String name) {
        return productService.findByProductName(token, name);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> update(
            @RequestHeader("token") String token,
            @RequestBody Product product) {
        return productService.save(token, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Object>> delete(
            @RequestHeader("token") String token,
            @PathVariable("id") Long id) {
        return productService.deleteById(token, id);
    }
}
