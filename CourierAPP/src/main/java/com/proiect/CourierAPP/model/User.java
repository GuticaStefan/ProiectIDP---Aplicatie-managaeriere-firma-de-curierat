package com.proiect.CourierAPP.model;

import com.proiect.CourierAPP.enums.Role;
import com.proiect.CourierAPP.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Order> userOrders = new HashSet<>();

    public User(String firstName, String lastName, String email, String phoneNumber, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
