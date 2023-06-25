package com.learning.product.controller;

import com.learning.product.model.dto.AuthRequest;
import com.learning.product.model.dto.JwtResponse;
import com.learning.product.model.dto.RefreshTokenRequest;
import com.learning.product.model.entity.RefreshToken;
import com.learning.product.model.entity.User;
import com.learning.product.service.impl.JwtService;
import com.learning.product.service.impl.RefreshTokenService;
import com.learning.product.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                ));

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());

            return JwtResponse.builder()
                     .accessToken(jwtService.generateToken(authRequest.getUsername()))
                     .token(refreshToken.getToken())
                     .build();
        } else
            throw new UsernameNotFoundException("Invalid user request !");

    }

    @PostMapping("/refresh-token")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database"));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String addNewUser(@RequestBody User user) {
        log.info("UserController:addNewUser.");
        return userService.addUser(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }






}
