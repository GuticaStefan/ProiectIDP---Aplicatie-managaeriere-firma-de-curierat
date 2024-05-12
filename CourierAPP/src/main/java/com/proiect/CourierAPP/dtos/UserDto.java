package com.proiect.CourierAPP.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proiect.CourierAPP.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role;

    private Set<AddOrderDto> userOrders = new HashSet<>();

    public UserDto(String firstName, String lastName, String email, String phoneNumber, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

}
