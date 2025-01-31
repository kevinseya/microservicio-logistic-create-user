package com.microserviciologistic.createuser.service;

import com.microserviciologistic.createuser.model.User;
import com.microserviciologistic.createuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private String webhookUrl = "http://23.22.142.172:5000/webhook_create_user";

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println("Save user on database: " + user);
            User createdUser = userRepository.save(user);
            notifyWebhook(createdUser);
            return createdUser;
        } catch (DataAccessException e) {
            System.err.println("Error to save user: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error connecting to the database",
                    e
            );
        }
    }

    private void notifyWebhook(User user) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            String jsonBody = String.format("{\"user_id\": \"%s\", \"name\": \"%s\", \"lastname\": \"%s\", \"email\": \"%s\", \"role\": \"%s\", \"message\": \"Usuario creado exitosamente\", \"date\": \"%s\"}",
                    user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getRole(), Instant.now().toString());

            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(webhookUrl, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Notification sent correctly.");
            } else {
                System.err.println("Error at sent notification on webhook: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error at sent notification on webhook: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
