package com.proiect.CourierAPPAuth.resource;

import com.proiect.CourierAPPAuth.dtos.AuthenticationRequest;
import com.proiect.CourierAPPAuth.dtos.UserDto;
import com.proiect.CourierAPPAuth.service.AuthenticationResponse;
import com.proiect.CourierAPPAuth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
