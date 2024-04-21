package com.proiect.CourierAPP.model;

import com.proiect.CourierAPP.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity(name = "OrderEntity")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private OrderStatus status;
    @ManyToOne
    @JoinColumn
    private User client;
}
