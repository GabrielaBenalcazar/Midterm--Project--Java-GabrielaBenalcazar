package com.ironhack.midterm.project.repository;

import com.ironhack.midterm.project.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
}