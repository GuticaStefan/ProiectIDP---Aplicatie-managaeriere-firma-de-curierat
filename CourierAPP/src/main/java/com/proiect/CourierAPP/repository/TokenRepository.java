package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findByToken(String token);

    Optional<List<Token>> findAllByUserId(UUID id);

    //void deleteById(Long id);
}