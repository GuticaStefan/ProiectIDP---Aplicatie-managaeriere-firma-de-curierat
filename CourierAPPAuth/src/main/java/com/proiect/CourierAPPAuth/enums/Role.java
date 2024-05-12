package com.proiect.CourierAPPAuth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Collections.emptySet());

    @Getter
    private final Set<Role> roles;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
