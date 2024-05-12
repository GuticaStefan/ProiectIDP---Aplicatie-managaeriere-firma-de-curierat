package com.proiect.CourierAPP.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String county;
    @NotBlank
    private String country;
    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CourierEmployee> locationEmployee = new HashSet<>();
}
