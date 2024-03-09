package com.example.demo.model.repository;

import com.example.demo.model.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Iterable<Product> findByProductName(@Param("name") String name);
}
