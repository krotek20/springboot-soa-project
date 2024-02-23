package com.soaproject.SecurityService.service;

import com.soaproject.SecurityService.model.UserCredentialsRequest;

public interface AuthService {
    long saveUser(UserCredentialsRequest userCredentialsRequest);
    String generateToken(String username);
    void validateToken(String token);
}
