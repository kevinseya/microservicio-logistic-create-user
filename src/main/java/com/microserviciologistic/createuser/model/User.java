package com.microserviciologistic.createuser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)    @Schema(description = "Unique identifier for the user",
            example = "550e8400-e29b-41d4-a716-446655440000",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Schema(description = "Name of the user", example = "John", required = true)
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Lastname is required")
    @Schema(description = "Lastname of the user", example = "Doe", required = true)
    private String lastname;

    @Column(nullable = false)
    @Email(message = "Email should be valid")
    @Schema(description = "Email of the user", example = "john.doe@example.com", required = true)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must have 10 digits")
    @Schema(description = "Phone number of the user", example = "1234567890", required = true)
    private String phone;

    @Column(nullable = false)
    @Size(min = 6, message = "Password must have at least 6 characters")
    @Schema(description = "Password of the user", example = "securePassword123", required = true)
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Role is required")
    @Schema(description = "Role of the user", example = "ADMIN", required = true)
    private String rol;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // Getter y Setter para name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter y Setter para lastname
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // Getter y Setter para email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter y Setter para phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter y Setter para password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter y Setter para rol
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
