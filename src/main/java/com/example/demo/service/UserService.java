package com.example.demo.service;

import com.example.demo.dto.ResponseDataLogin;
import com.example.demo.helpers.Jwt;
import com.example.demo.model.entity.User;
import com.example.demo.model.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Jwt jwt; // Inject Jwt bean here

    public ResponseEntity<ResponseDataLogin<User>> save(User user) {
        ResponseDataLogin<User> responseDataLogin = new ResponseDataLogin<>();

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            responseDataLogin.setStatusCode(400);
            responseDataLogin.setPayload(null);
            responseDataLogin.setMessage("Email and password cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDataLogin);
        }

        try {
            User savedUser = userRepo.save(user);

            String token = jwt.generateToken(savedUser.getEmail());

            responseDataLogin.setStatusCode(200);
            responseDataLogin.setPayload(savedUser);
            responseDataLogin.setToken(token);
            responseDataLogin.setMessage("User registered successfully!");

            return ResponseEntity.status(HttpStatus.OK).body(responseDataLogin);
        } catch (DataAccessException e) {
            responseDataLogin.setStatusCode(500);
            responseDataLogin.setMessage("Error occurred while inserting or updating user data");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDataLogin);
        } catch (Exception e) {
            responseDataLogin.setStatusCode(500);
            responseDataLogin.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDataLogin);
        }
    }

    public ResponseEntity<ResponseDataLogin<User>> login(User user) {
        ResponseDataLogin<User> responseDataLogin = new ResponseDataLogin<>();

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            responseDataLogin.setStatusCode(400);
            responseDataLogin.setPayload(null);
            responseDataLogin.setMessage("Email and password cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDataLogin);
        }

        try {
            // Retrieve user from the database based on the email
            Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());

            if (optionalUser.isPresent()) {
                User savedUser = optionalUser.get();
                if (savedUser.getPassword().equals(user.getPassword())) {
                    // Passwords match, generate token and send response
                    String token = jwt.generateToken(savedUser.getEmail());

                    responseDataLogin.setStatusCode(200);
                    responseDataLogin.setPayload(savedUser);
                    responseDataLogin.setToken(token);
                    responseDataLogin.setMessage("User logged in successfully!");

                    return ResponseEntity.status(HttpStatus.OK).body(responseDataLogin);
                } else {
                    // Passwords don't match
                    responseDataLogin.setStatusCode(401);
                    responseDataLogin.setMessage("Incorrect password");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDataLogin);
                }
            } else {
                // User not found
                responseDataLogin.setStatusCode(404);
                responseDataLogin.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDataLogin);
            }
        } catch (DataAccessException e) {
            // Database error
            responseDataLogin.setStatusCode(500);
            responseDataLogin.setMessage("Error occurred while accessing user data");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDataLogin);
        } catch (Exception e) {
            // Other unexpected errors
            responseDataLogin.setStatusCode(500);
            responseDataLogin.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDataLogin);
        }
    }

}
