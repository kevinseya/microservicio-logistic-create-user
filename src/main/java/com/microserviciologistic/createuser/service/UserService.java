package com.microserviciologistic.createuser.service;

import com.microserviciologistic.createuser.model.User;
import com.microserviciologistic.createuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email no puede ser nulo o vacío.");
        }

        try {
            System.out.println("Guardando el usuario en la base de datos: " + user);
            User createdUser = userRepository.save(user);
            notifyWebhook(createdUser);
            return createdUser;
        } catch (DataAccessException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error connecting to the database",
                    e
            );
        }
    }

    private void notifyWebhook(User user) {
        try {
            String webhookUrl = "http://localhost:5000/webhook_create_user";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            String jsonBody = String.format("{\"user_id\": \"%s\", \"name\": \"%s\", \"lastname\": \"%s\", \"email\": \"%s\", \"role\": \"%s\", \"message\": \"Usuario creado exitosamente\", \"date\": \"%s\"}",
                    user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getRole(), Instant.now().toString());


            // Envío de la solicitud POST con el cuerpo y las cabeceras
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // Realiza la solicitud POST al webhook y obtiene la respuesta
            ResponseEntity<String> response = restTemplate.exchange(webhookUrl, HttpMethod.POST, request, String.class);

            // Verificar el código de estado HTTP
            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Notificación enviada correctamente.");
            } else {
                System.err.println("Error al enviar la notificación al webhook: " + response.getStatusCode());
            }

        } catch (Exception e) {
            System.err.println("Error al enviar la notificación al webhook: " + e.getMessage());
            e.printStackTrace(); // Imprimir el stack trace para un mejor diagnóstico
        }
    }

}
