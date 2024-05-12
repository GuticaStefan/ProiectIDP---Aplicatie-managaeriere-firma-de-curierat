package com.proiect.CourierAPPAuth.service;

import com.proiect.CourierAPPAuth.enums.Role;
import com.proiect.CourierAPPAuth.exceptions.UserNotAuthenticatedException;
import com.proiect.CourierAPPAuth.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public boolean isAdmin() {
        if (tokenRepository.findAll().stream().findAny().isPresent()) {
            return tokenRepository.findAll().stream()
                    .anyMatch(t -> (!t.isExpired() &&
                            !t.isRevoked() &&
                            t.getUser().getRole().equals(Role.ADMIN)));
        } else {
            throw new UserNotAuthenticatedException();
        }
    }

    public boolean isAuthenticatedUser(String userName) {
        if (tokenRepository.findAll().stream().findAny().isPresent()) {
            return tokenRepository.findAll().stream()
                    .anyMatch(t -> (!t.isExpired() &&
                            !t.isRevoked() &&
                            t.getUser().getUsername().equals(userName)));
        } else {
            throw new UserNotAuthenticatedException();
        }
    }

    public void deleteById(UUID id) {
        tokenRepository.findAll().forEach(t -> {
            if (t.getId().equals(id)) {
                tokenRepository.deleteById(t.getId());
            }
        });
    }
}
