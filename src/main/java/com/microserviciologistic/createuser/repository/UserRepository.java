package com.microserviciologistic.createuser.repository;
import com.microserviciologistic.createuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing User entities.
 *
 * - Extends JpaRepository to provide CRUD operations.
 * - Uses UUID as the primary key type.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}