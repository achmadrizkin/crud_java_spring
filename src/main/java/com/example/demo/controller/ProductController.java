package com.example.demo.controller;

import com.example.demo.dto.ResponseData;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Product>>> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByProductName(@PathVariable("name") String name) {
        return productService.findByProductName(name);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> update(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Object>> delete(@PathVariable("id") Long id) {
        return productService.deleteById(id);
    }
}
