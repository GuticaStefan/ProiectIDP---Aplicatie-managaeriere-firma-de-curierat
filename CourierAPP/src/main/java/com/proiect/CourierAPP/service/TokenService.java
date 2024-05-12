package com.proiect.CourierAPP.service;

import com.proiect.CourierAPP.enums.Role;
import com.proiect.CourierAPP.exceptions.UserNotAuthenticatedException;
import com.proiect.CourierAPP.repository.TokenRepository;
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
}
