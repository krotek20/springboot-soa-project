package com.soaproject.SecurityService.service;

import java.util.Map;

public interface JwtService {
    String createToken(Map<String, Object> claims, String username);
    void validateToken(String token);
    String generateToken(String username);
}
