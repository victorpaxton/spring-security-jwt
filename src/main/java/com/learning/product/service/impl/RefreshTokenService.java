package com.learning.product.service.impl;

import com.learning.product.model.entity.RefreshToken;
import com.learning.product.repository.RefreshTokenRepository;
import com.learning.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByName(username).get())
                .token(UUID.randomUUID().toString())
                .expireDate(Instant.now().plusMillis(600000)) //10 minutes
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh Token expired");
        }

        return token;
    }
}
