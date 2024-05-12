package com.proiect.CourierAPP.service;
        import com.proiect.CourierAPP.dtos.AuthenticationRequest;
        import com.proiect.CourierAPP.dtos.UserDto;
        import com.proiect.CourierAPP.enums.Role;
        import com.proiect.CourierAPP.enums.TokenType;
        import com.proiect.CourierAPP.enums.UserType;
        import com.proiect.CourierAPP.exceptions.UserAlreadyExistsException;
        import com.proiect.CourierAPP.exceptions.UserNotFoundException;
        import com.proiect.CourierAPP.model.Order;
        import com.proiect.CourierAPP.model.Token;
        import com.proiect.CourierAPP.model.User;
        import com.proiect.CourierAPP.repository.TokenRepository;
        import com.proiect.CourierAPP.repository.UserRepository;
        import com.proiect.CourierAPP.security.JwtService;
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