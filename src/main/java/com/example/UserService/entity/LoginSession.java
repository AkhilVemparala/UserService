package com.example.UserService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "login_details")
public class LoginSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;

    private String userId;

    private LocalDateTime loginTime;

    private Boolean isActive;

    private LocalDateTime lastUpdated;

}



