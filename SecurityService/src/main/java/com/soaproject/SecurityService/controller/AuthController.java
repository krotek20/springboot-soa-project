package com.soaproject.SecurityService.controller;

import com.soaproject.SecurityService.model.AuthRequest;
import com.soaproject.SecurityService.model.UserCredentialsRequest;
import com.soaproject.SecurityService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody UserCredentialsRequest userCredentialsRequest) {
        return new ResponseEntity<>(authService.saveUser(userCredentialsRequest), HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return new ResponseEntity<>(authService.generateToken(authRequest.getUsername()), HttpStatus.OK);
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }
}
