package com.example.UserService.repository;

import com.example.UserService.entity.LoginSession;
import com.example.UserService.model.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LoginSessionRepository extends JpaRepository<LoginSession, String> {
    Optional<LoginSession> findByUserIdAndIsActiveTrue(String userId);
}