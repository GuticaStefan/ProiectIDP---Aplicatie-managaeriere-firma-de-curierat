package com.proiect.CourierAPP.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private String name;
    private String city;
    private String county;
    private String country;
}
