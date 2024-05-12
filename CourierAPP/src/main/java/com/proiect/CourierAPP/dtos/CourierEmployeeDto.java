package com.proiect.CourierAPP.dtos;

import com.proiect.CourierAPP.enums.EmployeeRole;
import com.proiect.CourierAPP.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierEmployeeDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String position;
    private Long badgeNumber;
}
