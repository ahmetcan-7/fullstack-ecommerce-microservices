package com.ahmetcan7.userservice.repository;

import com.ahmetcan7.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
