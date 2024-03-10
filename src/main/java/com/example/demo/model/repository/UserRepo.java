package com.example.demo.model.repository;

import com.example.demo.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
