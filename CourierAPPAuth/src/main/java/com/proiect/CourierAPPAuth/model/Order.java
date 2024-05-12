package com.proiect.CourierAPPAuth.model;

import com.proiect.CourierAPPAuth.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "OrderEntity")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private OrderStatus status;
    @NotBlank
    private String description;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
}
