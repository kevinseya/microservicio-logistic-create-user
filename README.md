# REST API in Java with Spring Boot

This project is a simple REST API created with Spring Boot that allows managing the USER domain, specifically for the CREATE microservice. The API offers the basic operation such as creating a new user, displaying the SWAGGER documentation technology screen as the main page.
## Project Structure

- **`CreateUserApplication.java`**: The main class that runs the Spring Boot application and defines the API controller.
      
- `POSTT /api/users/create`: Allows you to create the user, under the required columns.

## Requirements.

- **JDK 17** o superior.
- **Maven** (for dependency management and project construction).

## Installation.

1. **Clone the repository.**

    ```bash
    git clone <https://github.com/kevinseya/microservicio-logistic-create-user.git>
    ```

2. **Build and run the application** with Maven:

    ```bash
    mvn spring-boot:run
    ```

3. The application run on: `http://localhost:8080`.

## Use of endpoint

### 1. POST /api/users/create

Create a new user. The request body must contain the user details in JSON format.
POST request example:
```bash
POST /api/users/create Content-Type: application/json
    
    { 
    "name": "John", "lastname": "Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "password": "securePassword123",
    "role": "ADMIN" 
    }
```
**Response:**
```plaintext
    {
        "id": "550e8400-e29b-41d4-a716-446655440000",
        "name": "John",
        "lastname": "Doe",
        "email": "john.doe@example.com",
        "phone": "1234567890",
        "role": "ADMIN"
    }
```
**Response code:**
- **`201 Created:`** User created successfully.
- **`500 Internal Server Error:`** Server error.
