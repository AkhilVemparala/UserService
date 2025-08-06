package com.example.UserService.service;

import com.example.UserService.entity.UserDetails;
import com.example.UserService.model.LoginDetails;


public interface LoginService {

    public Boolean isValidUser(LoginDetails loginDetails);

    public Boolean registerUser(UserDetails userDetails) ;
}
