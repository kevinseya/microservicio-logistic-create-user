package com.microserviciologistic.createuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main class for the Create User microservice.
 *
 * - Uses Spring Boot to initialize and run the application.
 * - Acts as a controller to redirect root requests to the Swagger UI.
 */
@SpringBootApplication
@Controller
public class CreateUserApplication {

    /**
     * Main method to launch the Spring Boot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        SpringApplication.run(CreateUserApplication.class, args);
    }

    /**
     * Redirects the root URL ("/") to the Swagger UI.
     *
     * @return A redirect string to Swagger UI.
     */
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }


}
