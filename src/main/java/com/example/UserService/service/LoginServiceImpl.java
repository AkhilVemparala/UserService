package com.example.UserService.service;

import com.example.UserService.entity.LoginSession;
import com.example.UserService.entity.UserDetails;
import com.example.UserService.exception.InvalidCredentialsException;
import com.example.UserService.exception.UserNotFoundException;
import com.example.UserService.exception.UserServiceException;
import com.example.UserService.model.LoginDetails;
import com.example.UserService.repository.LoginSessionRepository;
import com.example.UserService.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserDetailsRepository customerRepository;

    @Autowired
    private LoginSessionRepository loginSessionRepository;

    @Override
    public Boolean isValidUser(LoginDetails loginDetails) {
        logger.info("Checking user is valid for  {}", loginDetails.getUserId());

        // Check if user exists
        UserDetails userDetails = customerRepository.findById(loginDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User record not found"));

        // Validate password
        if (!userDetails.getPassword().equals(loginDetails.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        // Check if active session exists
        Optional<LoginSession> existingSession = loginSessionRepository
                .findByUserIdAndIsActiveTrue(loginDetails.getUserId());

        if (existingSession.isPresent()) {
            // Just update the last updated timestamp
            LoginSession session = existingSession.get();
            session.setLastUpdated(LocalDateTime.now());
            loginSessionRepository.save(session);
            logger.info("Existing session updated for {}", loginDetails.getUserId());
        } else {
            // Create new session
            LoginSession session = new LoginSession();
            session.setUserId(loginDetails.getUserId());
            session.setLoginTime(LocalDateTime.now());
            session.setIsActive(true);
            session.setLastUpdated(LocalDateTime.now());

            loginSessionRepository.save(session);
            logger.info("New login session created for {}", loginDetails.getUserId());
        }

        return true;
    }


    @Override
    public Boolean registerUser(UserDetails userDetails) {
        logger.info("Registering user with details: {}", userDetails);
        UserDetails result = customerRepository.save(userDetails);
        logger.info("Response received {}",userDetails);
        if(result != null) {
            return true;
        }
        throw new UserServiceException("User registration failed");
    }
}