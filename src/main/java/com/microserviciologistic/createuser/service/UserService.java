package com.microserviciologistic.createuser.service;

import com.microserviciologistic.createuser.model.User;
import com.microserviciologistic.createuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email no puede ser nulo o vacío.");
        }

        try {
            System.out.println("Guardando el usuario en la base de datos: " + user);
            return userRepository.save(user);
        } catch (DataAccessException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error connecting to the database",
                    e
            );
        }
    }
}
