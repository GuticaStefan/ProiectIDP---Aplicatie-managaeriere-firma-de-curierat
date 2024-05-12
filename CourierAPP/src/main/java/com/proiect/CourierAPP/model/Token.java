package com.proiect.CourierAPP.model;

import com.proiect.CourierAPP.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public Instant expiryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
    public boolean revoked;
    public boolean expired;

}
