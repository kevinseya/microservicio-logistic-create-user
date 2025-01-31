package com.microserviciologistic.createuser.controller;

import com.microserviciologistic.createuser.model.User;
import com.microserviciologistic.createuser.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST Controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for creating users")
public class UserController {

    private final UserService userService;

    private String webhookUrl= "http://107.22.80.110:5000/webhook_create_user";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to create a new user.
     *
     * @param user The user data sent in the request body.
     * @return ResponseEntity with the created user and status code 201 (Created).
     */
    @PostMapping("/create")
    @Operation(summary = "Create user", description = "Endpoint to create users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            System.out.println("Received user: " + user.getEmail());
            System.out.println("Webhook URL in use: " + webhookUrl);

            User createdUser = userService.createUser(user);
            return ResponseEntity.status(201).body(createdUser);
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating user: " + e.getMessage(), e
            );
        }
    }
}
