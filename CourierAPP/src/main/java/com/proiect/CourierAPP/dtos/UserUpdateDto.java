package com.proiect.CourierAPP.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email
    private String email;
}
