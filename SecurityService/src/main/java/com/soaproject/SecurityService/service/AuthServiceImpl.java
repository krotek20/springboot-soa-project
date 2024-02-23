package com.soaproject.SecurityService.service;

import com.soaproject.SecurityService.entity.UserCredentials;
import com.soaproject.SecurityService.model.UserCredentialsRequest;
import com.soaproject.SecurityService.repository.UserCredentialsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public long saveUser(UserCredentialsRequest userCredentialsRequest) {
        log.info("Creating User...");

        UserCredentials userCredentials = UserCredentials
                .builder()
                .name(userCredentialsRequest.getName())
                .email(userCredentialsRequest.getEmail())
                .password(passwordEncoder.encode(userCredentialsRequest.getPassword()))
                .build();

        userCredentialsRepository.save(userCredentials);

        log.info("User created!");
        return userCredentials.getId();
    }

    @Override
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
