package com.proiect.CourierAPPAuth.service;

import com.proiect.CourierAPPAuth.dtos.AuthenticationRequest;
import com.proiect.CourierAPPAuth.dtos.UserDto;
import com.proiect.CourierAPPAuth.enums.Role;
import com.proiect.CourierAPPAuth.enums.TokenType;
import com.proiect.CourierAPPAuth.enums.UserType;
import com.proiect.CourierAPPAuth.exceptions.UserAlreadyExistsException;
import com.proiect.CourierAPPAuth.exceptions.UserNotFoundException;
import com.proiect.CourierAPPAuth.model.Order;
import com.proiect.CourierAPPAuth.model.Token;
import com.proiect.CourierAPPAuth.model.User;
import com.proiect.CourierAPPAuth.repository.TokenRepository;
import com.proiect.CourierAPPAuth.repository.UserRepository;
import com.proiect.CourierAPPAuth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthenticationResponse register(UserDto request) {
        var preExistentUser = userRepository.findByUserName(request.getUserName());

        if (preExistentUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }


        if (request.getEmail().contains("@courier.com")) {
            var user = User.builder()
                    .userName(request.getUserName())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .userType(UserType.CLIENT)
                    .phoneNumber(request.getPhoneNumber())
                    .userOrders(request.getUserOrders()
                            .stream().map(b -> modelMapper.map(b, Order.class))
                            .collect(Collectors.toSet()))
                    .build();

            var savedUser = userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        }

        var user = User.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .userType(UserType.CLIENT)
                .phoneNumber(request.getPhoneNumber())
                .userOrders(request.getUserOrders()
                        .stream().map(b -> modelMapper.map(b, Order.class))
                        .collect(Collectors.toSet()))
                .build();

        var savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var allValidTokens = tokenRepository.findAllByUserId(user.getId()).orElseThrow(UserNotFoundException::new);

        if (allValidTokens.isEmpty())
            return;
        allValidTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allValidTokens);
    }

}