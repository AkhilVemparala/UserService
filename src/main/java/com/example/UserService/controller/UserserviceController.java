package com.example.UserService.controller;

import com.example.UserService.entity.LoginSession;
import com.example.UserService.entity.UserDetails;
import com.example.UserService.exception.UserServiceException;
import com.example.UserService.model.LoginDetails;
import com.example.UserService.model.LoginResponseModel;
import com.example.UserService.repository.LoginSessionRepository;
import com.example.UserService.service.LoginService;
import com.example.UserService.service.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserserviceController {


    @Autowired
    private LoginService loginService;

    @Autowired
    LoginSessionRepository loginSessionRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseModel> Validate(@RequestBody LoginDetails login) {
        log.info("Trying to login user: {}", login.getUserId());
        Boolean result = loginService.isValidUser(login);
        if (!result) {
            throw new UserServiceException("User registration failed");
        }
        return new ResponseEntity<>(new LoginResponseModel(true, "User LogedIn successfully"), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<LoginResponseModel> registerUser(@RequestBody UserDetails userDetails) {
        // Assuming there's a method in LoginService to handle user registration
        log.info("Registering user: {}", userDetails.getUserId());
        Boolean result = loginService.registerUser(userDetails);
        if (!result) {
            throw new UserServiceException("User registration failed");
        }
        return new ResponseEntity<>(new LoginResponseModel(true, "User registered successfully"), HttpStatus.OK);
    }

    @GetMapping("/checkSession/{userId}")
    public ResponseEntity<LoginResponseModel>  CheckValidSession(@PathVariable String userId) {
        log.info("Checking session for user: {}", userId);
        Optional<LoginSession> session = loginSessionRepository.findByUserIdAndIsActiveTrue(userId);
        log.info("Checking session for user: {}", session);
        if (session.isEmpty()) {
            log.warn("No active session found for user: {}", userId);
            return new ResponseEntity<>(new LoginResponseModel(false, "No active session found"),HttpStatus.OK);
        }
    return new ResponseEntity<>(new LoginResponseModel(true, "Active session found"), HttpStatus.OK);
    }

    @PutMapping("/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable String userId) {
        LoginSession session = loginSessionRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active session not found"));

        session.setIsActive(false);
        session.setLastUpdated(LocalDateTime.now());
        loginSessionRepository.save(session);

        return ResponseEntity.ok("User logged out successfully.");
    }
}
