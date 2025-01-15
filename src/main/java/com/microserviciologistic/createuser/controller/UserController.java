package com.microserviciologistic.createuser.controller;

import com.microserviciologistic.createuser.model.User;
import com.microserviciologistic.createuser.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for create users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create user", description = "Endpoint to create users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            System.out.println("Recibido: " + user.getEmail());
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(201).body(createdUser);
        } catch (Exception e) {
            System.err.println("Error al crear el usuario: " + e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al crear el usuario: " + e.getMessage(),e
            );
        }
    }
}
