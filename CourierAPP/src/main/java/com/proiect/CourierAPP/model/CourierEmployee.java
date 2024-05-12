package com.proiect.CourierAPP.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourierEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String position;
    private Long badgeNumber;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Location location;
}
